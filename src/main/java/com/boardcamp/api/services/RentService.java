package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotAvailableException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.RentAlreadyFinalizedException;
import com.boardcamp.api.exceptions.RentNotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentRepository;

@Service
public class RentService {
    final RentRepository rentRepository;
    final CustomerRepository customerRepository;
    final GameRepository gameRepository;

    RentService(RentRepository rentRepository, CustomerRepository customerRepository, GameRepository gameRepository) {
        this.rentRepository = rentRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    public List<RentModel> findAll() {
        return rentRepository.findAll();
    }

    public RentModel save(RentDTO dto) {
        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found for this id")
        );

        GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(
            () -> new GameNotFoundException("Game not found for this id")
        );

        int gameRentalsQtd = rentRepository.countByAvaiableGameId(game.getId());

        if (gameRentalsQtd == game.getStockTotal())
            throw new GameNotAvailableException("This game is not available");
        
        RentModel rent = new RentModel(dto, customer, game);
        return rentRepository.save(rent);
    }

    public RentModel update(Long id) {
        RentModel rent = rentRepository.findById(id).orElseThrow(
            () -> new RentNotFoundException("Rent not found for this id")
        );

        if (rent.getReturnDate() != null)
            throw new RentAlreadyFinalizedException("This rent was already finalized");

        rent.setReturnDate(LocalDate.now());

        LocalDate expectedReturnDate = rent.getRentDate().plusDays(rent.getDaysRented());

        if (rent.getReturnDate().isAfter(expectedReturnDate)) {
            Long extraDays = expectedReturnDate.until(rent.getReturnDate(), ChronoUnit.DAYS);
            rent.setDelayFee(extraDays * (rent.getOriginalPrice() / rent.getDaysRented()));
        }

        return rentRepository.save(rent);
    }
}
