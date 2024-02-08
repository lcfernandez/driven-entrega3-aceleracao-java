package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GameDTO {
    @NotBlank(message = "Field 'name' is mandatory")
    private String name;

    private String image;

    @NotNull(message = "Field 'stockTotal' is mandatory")
    @Positive(message = "'stockTotal' must be greater than 0")
    private Integer stockTotal;

    @NotNull(message = "Field 'pricePerDay' is mandatory")
    @Positive(message = "'pricePerDay' must be greater than 0")
    private Integer pricePerDay;
}
