package com.devsu.client.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {

    private Long clientId;

    //@NotBlank(message = "El password es obligatorio.")
    private String password;

    @NotBlank(message = "El Nombre es obligatorio")
    private String name;

    @NotNull(message = "El genero es obligatorio")
    private char gender;

    @NotNull(message = "La Fecha de Nacimiento es obligatorio")
    @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
    private LocalDate birthdate;

    @NotBlank(message = "El número de documento e obligatorio")
    @Size(max = 8, message = "El número de documento no debe exceder los 8 dígitos")
    private String documentNumber;

    @NotBlank(message = "La Dirección es obligatoria")
    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
    private String address;

    @NotBlank(message = "El Celular el obligatorio")
    @Size(max = 9, message = "El número de celular debe tener 9 dígitos")
    private String phone;


}
