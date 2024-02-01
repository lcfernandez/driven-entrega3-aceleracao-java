package com.boardcamp.api.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    final CustomerService customerService;

    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGame(@PathVariable Long id) {
        Optional<CustomerModel> customer = customerService.findById(id);

        if (!customer.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This id doesn't exist");

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerDTO body) {
        Optional<CustomerModel> customer = customerService.save(body);

        if (!customer.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Repeated cpf");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
}
