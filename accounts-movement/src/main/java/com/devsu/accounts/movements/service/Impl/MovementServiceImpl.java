package com.devsu.accounts.movements.service.Impl;

import com.devsu.accounts.movements.api.dto.AccountDTO;
import com.devsu.accounts.movements.api.dto.MovementDTO;
import com.devsu.accounts.movements.api.exceptions.InsufficientBalanceException;
import com.devsu.accounts.movements.api.mapper.MovementMapper;
import com.devsu.accounts.movements.domain.entities.Account;
import com.devsu.accounts.movements.domain.entities.Movement;
import com.devsu.accounts.movements.domain.repositories.AccountRepository;
import com.devsu.accounts.movements.domain.repositories.MovementRepository;
import com.devsu.accounts.movements.service.MovementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MovementMapper movementMapper;

    @Override
    public List<MovementDTO> getAllMovements() {
        return movementRepository.findAll()
                .stream().filter(Movement::getActive)
                .map(movementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovementDTO> getMovementById(Long movementId) {
        return movementRepository.findById(movementId)
                .filter(Movement::getActive)

                .map(movementMapper::toDTO);
    }

    @Transactional
    @Override
    public Optional<MovementDTO> createMovement(MovementDTO movementDTO) {

        return accountRepository.findById(movementDTO.getAccountId())
                .map(account -> {

                    if (account.getBalance().add(movementDTO.getAmount()).compareTo(BigDecimal.valueOf(0)) < 0) {
                        throw new InsufficientBalanceException("Saldo insuficiente para completar la operación.");
                    }

                    Movement movement = movementMapper.toEntity(movementDTO);
                    movement.setDate(LocalDateTime.now());
                    movement.setBalance(account.getBalance().add(movement.getAmount()));
                    movement.setAccount(account);

                    Movement movementResult = movementRepository.save(movement);
                    account.setPreviousBalance(account.getBalance());
                    account.setBalance(account.getBalance().add(movement.getAmount()));
                    Account accountResult = accountRepository.save(account);

                    return movementResult;
                })
                .map(movementMapper::toDTO);
    }

    @Transactional
    @Override
    public Optional<MovementDTO> updateMovement(Long movementId, MovementDTO movementDTO) {
        return movementRepository.findById(movementId)
                .map(existAccount -> {
                    movementMapper.updateEntityFromDTO(movementDTO, existAccount);
                    return movementRepository.save(existAccount);
                })
                .map(movementMapper::toDTO);
    }

    @Transactional
    @Override
    public Optional<MovementDTO> partiallyUpdateMovement(Long movementId, Map<String, Object> movementMap) {
        return movementRepository.findById(movementId)
                .map(existAccount -> {
                    movementMapper.updateEntityPartial(movementMap, existAccount);
                    return movementRepository.save(existAccount);
                })
                .map(movementMapper::toDTO);
    }

    @Transactional
    @Override
    public Optional<MovementDTO> deleteMovement(Long movementId) {
        return movementRepository.findById(movementId)
                .map(movement -> {
                    movement.setActive(false);
                    return movementMapper.toDTO(movementRepository.save(movement));
                });
    }
}
