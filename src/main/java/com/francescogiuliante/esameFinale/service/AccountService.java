package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.AccountDTO;
import com.francescogiuliante.esameFinale.dto.response.AccountResponseDTO;
import com.francescogiuliante.esameFinale.mapper.AccountMapper;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    public List<AccountResponseDTO> getAllAccounts() {
        log.info("Fetching all accounts.");
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponseDTO> responseDTOs = new ArrayList<>();
        for (Account account : accounts) {
            responseDTOs.add(accountMapper.toResponseDTO(account));
        }
        return responseDTOs;
    }

    public Optional<AccountResponseDTO> getAccountById(Long id) {
        log.info("Fetching account with ID: {}", id);
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return Optional.of(accountMapper.toResponseDTO(accountOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public AccountResponseDTO createAccount(AccountDTO accountDTO) {
        log.info("Creating a new account: {}", accountDTO);
        Account account = accountMapper.toEntity(accountDTO);
        account.setRegistrationDate(LocalDate.now());
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponseDTO(savedAccount);
    }

    public Optional<AccountResponseDTO> updateAccount(Long id, AccountDTO accountDTO) {
        log.info("Updating account with ID {}: {}", id, accountDTO);
        Optional<Account> existingAccountOptional = accountRepository.findById(id);
        if (existingAccountOptional.isPresent()) {
            Account existingAccount = existingAccountOptional.get();
            Account updatedAccount = accountMapper.toEntity(accountDTO);
            updatedAccount.setId(id);
            updatedAccount.setRegistrationDate(existingAccount.getRegistrationDate());
            Account savedAccount = accountRepository.save(updatedAccount);
            return Optional.of(accountMapper.toResponseDTO(savedAccount));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteAccount(Long id) {
        log.info("Deleting account with ID: {}", id);
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<AccountResponseDTO> getAccountByPhone(String phone) {
        log.info("Fetching account with phone number: {}", phone);
        Optional<Account> accountOptional = accountRepository.findByPhone(phone);
        if (accountOptional.isPresent()) {
            return Optional.of(accountMapper.toResponseDTO(accountOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public List<AccountResponseDTO> getAccountsByLastName(String lastName) {
        log.info("Fetching accounts with last name: {}", lastName);
        List<Account> accounts = accountRepository.findByLastName(lastName);
        List<AccountResponseDTO> responseDTOs = new ArrayList<>();
        for (Account account : accounts) {
            responseDTOs.add(accountMapper.toResponseDTO(account));
        }
        return responseDTOs;
    }
}