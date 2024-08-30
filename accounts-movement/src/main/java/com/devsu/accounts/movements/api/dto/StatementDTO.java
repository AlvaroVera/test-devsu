package com.devsu.accounts.movements.api.dto;



import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StatementDTO {

    private String name;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private BigDecimal previousBalance;
    private List<MovementDTO> movementList;

}
