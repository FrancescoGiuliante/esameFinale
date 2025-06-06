package com.francescogiuliante.esameFinale.repository;

import com.francescogiuliante.esameFinale.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByLastName(String lastName);

    Optional<Account> findByPhone(String phone);
}