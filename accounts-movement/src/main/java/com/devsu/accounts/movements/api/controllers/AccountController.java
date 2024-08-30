package com.devsu.accounts.movements.api.controllers;

import com.devsu.accounts.movements.api.dto.AccountDTO;
import com.devsu.accounts.movements.api.dto.StatementDTO;
import com.devsu.accounts.movements.api.exceptions.ResourceNotFoundException;
import com.devsu.accounts.movements.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/statement")
    public ResponseEntity<List<StatementDTO>> getStatement(
            @RequestParam(value = "date-from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(value = "date-to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
            @RequestParam(value = "client-id") Long clientId
    ){
        return ResponseEntity.ok(accountService.getStatement(dateFrom, dateTo, clientId));
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountById(accountNumber)
                .map(accountDTO -> new ResponseEntity<>(accountDTO, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + accountNumber));
    }
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("accountNumber") String accountNumber,
                                                    @Valid @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccount(accountNumber, accountDTO)
                .map(accountDTO1 -> new ResponseEntity<>(accountDTO1, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + accountNumber));
    }
    @PatchMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> partiallyUpdateAccount(@PathVariable("accountNumber") String accountNumber,
                                                             @RequestBody Map<String, Object> accountMap) {
        return accountService.partiallyUpdateAccount(accountNumber, accountMap)
                .map(accountDTO1 -> new ResponseEntity<>(accountDTO1, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + accountNumber));
    }
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountNumber") String accountNumber) {

        return accountService.deleteAccount(accountNumber)
                .map(accountDTO1 -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + accountNumber));
    }


}
