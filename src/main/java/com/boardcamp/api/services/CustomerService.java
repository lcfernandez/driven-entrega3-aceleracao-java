package com.boardcamp.api.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<CustomerModel> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerModel> save(CustomerDTO dto) {
        if (customerRepository.existsByCpf(dto.getCpf()))
            return Optional.empty();

        CustomerModel customer = new CustomerModel(dto);
        return Optional.of(customerRepository.save(customer));
    }
}
