package com.devsu.accounts.movements.api.mapper;

import com.devsu.accounts.movements.api.dto.MovementDTO;
import com.devsu.accounts.movements.domain.entities.Movement;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


@Component
public class MovementMapper {
    public MovementDTO toDTO(Movement movement) {
        if (movement == null) {
            return null;
        }

        MovementDTO movementDTO = new MovementDTO();
        movementDTO.setMovementId(movement.getMovementId());
        movementDTO.setDate(movement.getDate());
        movementDTO.setMovementType(movement.getMovementType());
        movementDTO.setAmount(movement.getAmount());
        movementDTO.setBalance(movement.getBalance());
        movementDTO.setAccountId(movement.getAccount().getAccountId());
        movementDTO.setAccountNumber(movement.getAccount().getAccountNumber());

        return movementDTO;
    }

    public Movement toEntity(MovementDTO movementDTO) {
        if (movementDTO == null) {
            return null;
        }

        Movement movement = new Movement();
        movement.setDate(movementDTO.getDate());
        movement.setMovementType(movementDTO.getMovementType());
        movement.setAmount(movementDTO.getAmount());
        movement.setBalance(movementDTO.getBalance());

        return movement;
    }


    public void updateEntityFromDTO(MovementDTO movementDTO, Movement movement) {
        if (movementDTO == null) {
            return;
        }

        if (movementDTO.getDate() != null) {
            movement.setDate(movementDTO.getDate());
        }
        if (movementDTO.getMovementType() != null) {
            movement.setMovementType(movementDTO.getMovementType());
        }
        if (movementDTO.getAmount() != null) {
            movement.setAmount(movementDTO.getAmount());
        }
        if (movementDTO.getBalance() != null) {
            movement.setBalance(movementDTO.getBalance());
        }
    }

    public void updateEntityPartial(Map<String, Object> movementMap, Movement movement) {


        if (movementMap.get("date") != null) {
            movement.setDate((LocalDateTime) movementMap.get("date"));
        }
        if (movementMap.get("movementType") != null) {
            movement.setMovementType((String) movementMap.get("movementType"));
        }
        if (movementMap.get("amount") != null) {
            movement.setAmount((BigDecimal) movementMap.get("amount"));
        }
        if (movementMap.get("balance") != null) {
            movement.setBalance((BigDecimal) movementMap.get("balance"));
        }
    }
}
