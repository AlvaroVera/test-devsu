package com.devsu.accounts.movements.domain.repositories;

import com.devsu.accounts.movements.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByClientId(Long clientId);

    List<Account> findByClientIdAndMovements_DateBetween(Long clientId, LocalDateTime dateStart, LocalDateTime dateEnd);

}
