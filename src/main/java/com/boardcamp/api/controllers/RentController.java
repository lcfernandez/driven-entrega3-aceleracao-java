package com.boardcamp.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentDTO;
import com.boardcamp.api.models.RentModel;
import com.boardcamp.api.services.RentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rentals")
public class RentController {
    final RentService rentService;

    RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public ResponseEntity<List<RentModel>> findAll() {
        List<RentModel> rentals = rentService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }

    @PostMapping
    public ResponseEntity<RentModel> createRent(@RequestBody @Valid RentDTO body) {
        RentModel rent = rentService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(rent);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<RentModel> updateRent(@PathVariable("id") Long id) {
        RentModel rent = rentService.update(id);
        return ResponseEntity.status(HttpStatus.OK).body(rent);
    }
}
