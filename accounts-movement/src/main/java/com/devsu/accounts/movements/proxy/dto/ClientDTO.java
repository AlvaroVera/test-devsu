package com.devsu.accounts.movements.proxy.dto;

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
    private String password;
    private String name;
    private char gender;
    private LocalDate birthdate;
    private String documentNumber;
    private String address;
    private String phone;


}
