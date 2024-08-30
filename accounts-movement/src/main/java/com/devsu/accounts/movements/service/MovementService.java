package com.devsu.accounts.movements.service;

import com.devsu.accounts.movements.api.dto.MovementDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovementService {
    List<MovementDTO> getAllMovements();
    Optional<MovementDTO> getMovementById(Long movementId);
    Optional<MovementDTO> createMovement(MovementDTO movementDTO);
    Optional<MovementDTO> updateMovement(Long movementId, MovementDTO movementDTO);
    Optional<MovementDTO> partiallyUpdateMovement(Long movementId, Map<String, Object> movementMap);
    Optional<MovementDTO> deleteMovement(Long movementId);
}
