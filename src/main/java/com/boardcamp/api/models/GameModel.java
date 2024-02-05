package com.boardcamp.api.models;

import com.boardcamp.api.dtos.GameDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String image;

    @Column(nullable = false)
    @Min(value = 0)
    private int stockTotal;

    @Column(nullable = false)
    @Min(value = 0)
    private int pricePerDay;

    public GameModel(GameDTO dto) {
        this.name = dto.getName();
        this.image = dto.getImage();
        this.stockTotal = dto.getStockTotal();
        this.pricePerDay = dto.getPricePerDay();
    }
}
