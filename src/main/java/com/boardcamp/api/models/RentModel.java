package com.boardcamp.api.models;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.boardcamp.api.dtos.RentDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate rentDate;

    @Column(nullable = false)
    @Min(value = 1)
    private int daysRented;

    @Column
    private LocalDate returnDate;

    @Column(nullable = false)
    @Min(value = 0)
    private int originalPrice;

    @Column(nullable = false)
    private Long delayFee;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameModel game;

    public RentModel(RentDTO dto, CustomerModel customer, GameModel game) {
        this.daysRented = dto.getDaysRented();
        this.originalPrice = game.getPricePerDay() * this.daysRented;
        this.delayFee = 0L;
        this.customer = customer;
        this.game = game;
    }
}
