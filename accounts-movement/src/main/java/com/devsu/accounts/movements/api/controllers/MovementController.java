package com.devsu.accounts.movements.api.controllers;


import com.devsu.accounts.movements.api.dto.MovementDTO;
import com.devsu.accounts.movements.api.exceptions.ResourceNotFoundException;
import com.devsu.accounts.movements.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/movements")
@Validated
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @GetMapping
    public ResponseEntity<List<MovementDTO>> getAllMovements() {
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementDTO> getMovementById(@PathVariable("id") Long movementId) {

        return movementService.getMovementById(movementId)
                .map(accountDTO -> new ResponseEntity<>(accountDTO, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado: " + movementId));
    }

    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@Valid @RequestBody MovementDTO movementDTO) {
        return movementService.createMovement(movementDTO)
                .map(accountDTO -> new ResponseEntity<>(accountDTO, HttpStatus.CREATED))
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + movementDTO.getAccountId()));
    }
    @PutMapping("/{id}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable("id") Long movementId,
                                                      @Valid @RequestBody MovementDTO movementDTO) {
        return movementService.updateMovement(movementId, movementDTO)
                .map(accountDTO1 -> new ResponseEntity<>(accountDTO1, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado: " + movementId));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<MovementDTO> partiallyUpdateMovement(@PathVariable("id") Long movementId,
                                                               @RequestBody Map<String, Object> movementMap) {
        return movementService.partiallyUpdateMovement(movementId, movementMap)
                .map(accountDTO1 -> new ResponseEntity<>(accountDTO1, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado: " + movementId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MovementDTO> deleteMovement(@PathVariable("id") Long movementId) {
        return movementService.deleteMovement(movementId)
                .map(accountDTO1 -> new ResponseEntity<MovementDTO>(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado: " + movementId));
    }


}
