package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

		doReturn(0).when(rentRepository).countUnfinishedRentalsByGameId(any());
		doReturn(Optional.of(customer)).when(customerRepository).findById(any());
		doReturn(Optional.of(game)).when(gameRepository).findById(any());
		doReturn(newRent).when(rentRepository).save(any());

		// when
		RentModel result = rentService.save(rentDto);

		// then
		assertNotNull(result);
		verify(rentRepository, times(1)).countUnfinishedRentalsByGameId(any());
		verify(customerRepository, times(1)).findById(any());
		verify(gameRepository, times(1)).findById(any());
		verify(rentRepository, times(1)).save(any());
		assertEquals(newRent, result);
	}

}
