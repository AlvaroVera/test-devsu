package com.devsu.accounts.movements.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementDTO {

    private Long movementId;

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long accountId;

    private String accountNumber;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Size(max = 50, message = "El tipo de movimiento no puede tener más de 50 caracteres")
    private String movementType;

    @NotNull(message = "El monto es obligatorio")
    @Digits(integer = 13, fraction = 2, message = "El monto debe ser un decimal con hasta 13 dígitos enteros y 2 decimales")
    private BigDecimal amount;

    private BigDecimal balance;

    private LocalDateTime date;
}