package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotBlank(message = "Field 'name' is mandatory")
    private String name;

    @NotBlank(message = "Field 'cpf' is mandatory")
    @Size(min = 11, max = 11, message = "Field 'cpf' must have 11 digits")
    private String cpf;
}
