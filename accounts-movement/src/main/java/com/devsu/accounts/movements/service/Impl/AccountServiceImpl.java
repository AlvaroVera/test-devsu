package com.devsu.accounts.movements.service.Impl;

import com.devsu.accounts.movements.api.dto.AccountDTO;
import com.devsu.accounts.movements.api.dto.StatementDTO;
import com.devsu.accounts.movements.api.exceptions.ResourceNotFoundException;
import com.devsu.accounts.movements.api.mapper.AccountMapper;
import com.devsu.accounts.movements.domain.entities.Account;
import com.devsu.accounts.movements.domain.entities.Movement;
import com.devsu.accounts.movements.domain.repositories.AccountRepository;
import com.devsu.accounts.movements.domain.repositories.MovementRepository;
import com.devsu.accounts.movements.proxy.client.Client;
import com.devsu.accounts.movements.proxy.dto.ClientDTO;
import com.devsu.accounts.movements.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;
    private final Client client;
    private final MovementRepository movementRepository;

    @Override
    public List<StatementDTO> getStatement(LocalDate dateFrom, LocalDate dateTo, Long clientId) {

        ClientDTO clientDTO = client.getClientById(clientId);
        List<Account> accountList = accountRepository.findByClientIdAndMovements_DateBetween(clientId, dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
        return accountMapper.toStatementAccount(clientDTO.getName(), accountList);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream().filter(Account::getActive)
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AccountDTO> getAccountById(String accountNumber) {


        return accountRepository.findByAccountNumber(accountNumber)
                .filter(Account::getActive)
                .map(account1 -> {
                    ClientDTO clientDTO = client.getClientById(account1.getClientId());
                    account1.setClientName(clientDTO.getName());
                    return account1;
                })
                .map(accountMapper::toDTO);
    }

    @Override
    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {

        ClientDTO clientDTO = client.getClientById(accountDTO.getClientId());

        if (clientDTO == null){
            throw new ResourceNotFoundException("No se encontro el cliente con el id: " + accountDTO.getClientId());
        }

        Account account = accountRepository.save(accountMapper.toEntity(accountDTO));

        if (account.getBalance().compareTo(BigDecimal.valueOf(0.0)) > 0) {
            Movement movement = new Movement();
            movement.setDate(LocalDateTime.now());
            movement.setMovementType("Apertura de cuenta");
            movement.setAmount(accountDTO.getBalance());
            movement.setBalance(accountDTO.getBalance());
            movement.setAccount(account);
            movementRepository.save(movement);
        }

        return accountMapper.toDTO(account);
    }

    @Override
    @Transactional
    public Optional<AccountDTO> updateAccount(String accountNumber, AccountDTO accountDTO) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(existAccount -> {
                    accountMapper.updateEntityFromDTO(accountDTO, existAccount);
                    return accountRepository.save(existAccount);
                })
                .map(accountMapper::toDTO);
    }

    @Override
    @Transactional
    public Optional<AccountDTO> partiallyUpdateAccount(String accountNumber, Map<String, Object> accountMap) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(existAccount -> {
                    accountMapper.updateEntityPartial(accountMap, existAccount);
                    return accountRepository.save(existAccount);
                })
                .map(accountMapper::toDTO);
    }

    @Override
    @Transactional
    public Optional<AccountDTO> deleteAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(existingAccount -> {
                    existingAccount.setActive(false);
                    existingAccount.getMovements().forEach(movement -> movement.setActive(false));
                    return accountMapper.toDTO(accountRepository.save(existingAccount));
                });


    }
}
