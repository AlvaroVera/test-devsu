package com.devsu.accounts.movements.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private Long accountId;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(max = 50, message = "El número de cuenta no puede tener más de 50 caracteres")
    private String accountNumber;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Size(max = 50, message = "El tipo de cuenta no puede tener más de 50 caracteres")
    private String accountType;

    @NotNull(message = "El saldo es obligatorio")
    @Digits(integer = 13, fraction = 2, message = "El saldo debe ser un decimal con hasta 13 dígitos enteros y 2 decimales")
    private BigDecimal balance;

    private BigDecimal previousBalance;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede tener más de 50 caracteres")
    private String status;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;

    private String clientName;
}
