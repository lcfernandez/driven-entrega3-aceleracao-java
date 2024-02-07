package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.RentDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotAvailableException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentRepository;
import com.boardcamp.api.services.RentService;

@SpringBootTest
class RentUnitTests {

	@InjectMocks
    private RentService rentService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private GameRepository gameRepository;
    
    @Mock
    private RentRepository rentRepository;
	
    @Test
	void givenValidRent_whenCreatingRent_thenCreatesRent() {
		// given
		RentDTO rentDto = new RentDTO(1L, 1L, 1);
		CustomerModel customer = new CustomerModel(1L, "Name", "12345678910");
		GameModel game = new GameModel(1L, "Name", "Image", 1, 100);
		RentModel newRent = new RentModel(rentDto, customer, game);

		doReturn(Optional.of(customer)).when(customerRepository).findById(any());
		doReturn(Optional.of(game)).when(gameRepository).findById(any());
		doReturn(0).when(rentRepository).countUnfinishedRentalsByGameId(any());
		doReturn(newRent).when(rentRepository).save(any());

		// when
		RentModel result = rentService.save(rentDto);

		// then
		assertNotNull(result);
		verify(customerRepository, times(1)).findById(any());
		verify(gameRepository, times(1)).findById(any());
		verify(rentRepository, times(1)).countUnfinishedRentalsByGameId(any());
		verify(rentRepository, times(1)).save(any());
		assertEquals(newRent, result);
	}

	@Test
	void givenWrongCustomerId_whenCreatingRent_thenThrowsError() {
		// given
		RentDTO rentDto = new RentDTO(1L, 1L, 1);

		doReturn(Optional.empty()).when(customerRepository).findById(any());

		// when
		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> rentService.save(rentDto));

		// then
		assertNotNull(exception);
		verify(customerRepository, times(1)).findById(any());
		verify(rentRepository, times(0)).save(any());
		assertEquals("Customer not found for this id", exception.getMessage());
	}

	@Test
	void givenWrongGameId_whenCreatingRent_thenThrowsError() {
		// given
		RentDTO rentDto = new RentDTO(1L, 1L, 1);
		CustomerModel customer = new CustomerModel(1L, "Name", "12345678910");

		doReturn(Optional.empty()).when(customerRepository).findById(any());
		doReturn(Optional.of(customer)).when(customerRepository).findById(any());
		doReturn(Optional.empty()).when(gameRepository).findById(any());

		// when
		GameNotFoundException exception = assertThrows(GameNotFoundException.class, () -> rentService.save(rentDto));

		// then
		assertNotNull(exception);
		verify(customerRepository, times(1)).findById(any());
		verify(gameRepository, times(1)).findById(any());
		verify(rentRepository, times(0)).save(any());
		assertEquals("Game not found for this id", exception.getMessage());
	}

	@Test
	void givenNotAvailableGame_whenCreatingRent_thenThrowsError() {
		// given
		RentDTO rentDto = new RentDTO(1L, 1L, 1);
		CustomerModel customer = new CustomerModel(1L, "Name", "12345678910");
		GameModel game = new GameModel(1L, "Name", "Image", 1, 100);

		doReturn(Optional.empty()).when(customerRepository).findById(any());
		doReturn(Optional.of(customer)).when(customerRepository).findById(any());
		doReturn(Optional.of(game)).when(gameRepository).findById(any());
		doReturn(1).when(rentRepository).countUnfinishedRentalsByGameId(any());

		// when
		GameNotAvailableException exception = assertThrows(GameNotAvailableException.class, () -> rentService.save(rentDto));

		// then
		assertNotNull(exception);
		verify(customerRepository, times(1)).findById(any());
		verify(gameRepository, times(1)).findById(any());
		verify(rentRepository, times(1)).countUnfinishedRentalsByGameId(any());
		verify(rentRepository, times(0)).save(any());
		assertEquals("This game is not available", exception.getMessage());
	}

}
