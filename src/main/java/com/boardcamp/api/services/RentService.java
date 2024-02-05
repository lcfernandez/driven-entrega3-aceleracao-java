package com.boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentDTO;
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

    public Optional<RentModel> save(RentDTO dto) {
        Optional<CustomerModel> customer = customerRepository.findById(dto.getCustomerId());

        if (!customer.isPresent())
            return Optional.empty();

        Optional<GameModel> game = gameRepository.findById(dto.getGameId());
        
        if (!game.isPresent())
            return Optional.empty();

        int gameRentalsQtd = rentRepository.countByAvaiableGameId(game.get().getId());

        if (gameRentalsQtd == game.get().getStockTotal())
            return Optional.empty();
        
        RentModel rent = new RentModel(dto, customer.get(), game.get());
        return Optional.of(rentRepository.save(rent));
    }
}
