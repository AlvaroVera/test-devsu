package com.devsu.accounts.movements.domain.repositories;

import com.devsu.accounts.movements.domain.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccountAccountId(Long accountId);
}