package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentDTO {
    @NotNull(message = "Field 'customerId' is mandatory")
    private Long customerId;

    @NotNull(message = "Field 'gameId' is mandatory")
    private Long gameId;

    @NotNull(message = "Field 'daysRentend' is mandatory")
    @Positive(message = "'daysRented' must be greater than 0")
    private Integer daysRented;
}
