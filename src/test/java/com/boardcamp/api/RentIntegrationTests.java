package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.RentDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RentIntegrationTests {
    @Autowired
	private TestRestTemplate restTemplate;

    @Autowired
	private CustomerRepository customerRepository;

    @Autowired
	private GameRepository gameRepository;

    @Autowired
	private RentRepository rentRepository;

    @AfterEach
    void cleanUpDatabase() {
        rentRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    void givenValidRent_whenCreatingRent_thenCreatesRent() {
        // given
        CustomerModel customer = new CustomerModel(null, "Name", "12345678910");
        CustomerModel createdCustomer = customerRepository.save(customer);

        GameModel game = new GameModel(null, "Name", "Image", 1, 100);
        GameModel createdGame = gameRepository.save(game);

        RentDTO rent = new RentDTO(createdCustomer.getId(), createdGame.getId(), 1);
        HttpEntity<RentDTO> body = new HttpEntity<>(rent);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            String.class
        );

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, rentRepository.count());
    }

    @Test
	void givenWrongCustomerId_whenCreatingRent_thenThrowsError() {
		// given
        CustomerModel customer = new CustomerModel(null, "Name", "12345678910");
        CustomerModel createdCustomer = customerRepository.save(customer);
        customerRepository.deleteById(createdCustomer.getId());

        GameModel game = new GameModel(null, "Name", "Image", 1, 100);
        GameModel createdGame = gameRepository.save(game);

        RentDTO rent = new RentDTO(createdCustomer.getId(), createdGame.getId(), 1);
        HttpEntity<RentDTO> body = new HttpEntity<>(rent);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            String.class
        );

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, rentRepository.count());
    }

    @Test
	void givenWrongGameId_whenCreatingRent_thenThrowsError() {
        // given
        CustomerModel customer = new CustomerModel(null, "Name", "12345678910");
        CustomerModel createdCustomer = customerRepository.save(customer);

        GameModel game = new GameModel(null, "Name", "Image", 1, 100);
        GameModel createdGame = gameRepository.save(game);
        gameRepository.deleteById(createdGame.getId());

        RentDTO rent = new RentDTO(createdCustomer.getId(), createdGame.getId(), 1);
        HttpEntity<RentDTO> body = new HttpEntity<>(rent);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            String.class
        );

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, rentRepository.count());
    }

    @Test
	void givenNotAvailableGame_whenCreatingRent_thenThrowsError() {
        // given
        CustomerModel customerSuccess = new CustomerModel(null, "Name", "12345678910");
        CustomerModel createdCustomerSuccess = customerRepository.save(customerSuccess);

        CustomerModel customerFailure = new CustomerModel(null, "Name", "23456789101");
        CustomerModel createdCustomerFailure = customerRepository.save(customerFailure);

        GameModel game = new GameModel(null, "Name", "Image", 1, 100);
        GameModel createdGame = gameRepository.save(game);

        RentDTO rentSuccess = new RentDTO(createdCustomerSuccess.getId(), createdGame.getId(), 1);
        HttpEntity<RentDTO> bodySuccess = new HttpEntity<>(rentSuccess);

        RentDTO rentFailure = new RentDTO(createdCustomerFailure.getId(), createdGame.getId(), 1);
        HttpEntity<RentDTO> bodyFailure = new HttpEntity<>(rentFailure);

        // when
        restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            bodySuccess,
            String.class
        );

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            bodyFailure,
            String.class
        );

        // then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, rentRepository.count());
    }
}
