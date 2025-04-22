package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.AccountDTO;
import com.francescogiuliante.esameFinale.dto.response.AccountResponseDTO;
import com.francescogiuliante.esameFinale.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account", description = "Operations related to accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @Operation(summary = "Get all accounts")
    @ApiResponse(responseCode = "200", description = "List of all accounts")
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        log.info("Received GET request for /api/accounts");
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id) {
        log.info("Received GET request for /api/accounts/{}", id);
        Optional<AccountResponseDTO> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    @ApiResponse(responseCode = "201", description = "Account created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        log.info("Received POST request for /api/accounts with body: {}", accountDTO);
        AccountResponseDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    @ApiResponse(responseCode = "200", description = "Account updated")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        log.info("Received PUT request for /api/accounts/{} with body: {}", id, accountDTO);
        Optional<AccountResponseDTO> updatedAccount = accountService.updateAccount(id, accountDTO);
        return updatedAccount.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account by ID")
    @ApiResponse(responseCode = "204", description = "Account deleted")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/accounts/{}", id);
        if (accountService.deleteAccount(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-phone/{phone}")
    @Operation(summary = "Get account by phone number")
    @ApiResponse(responseCode = "200", description = "Account found")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<AccountResponseDTO> getAccountByPhone(@PathVariable String phone) {
        log.info("Received GET request for /api/accounts/by-phone/{}", phone);
        Optional<AccountResponseDTO> account = accountService.getAccountByPhone(phone);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-last-name/{lastName}")
    @Operation(summary = "Get accounts by last name")
    @ApiResponse(responseCode = "200", description = "List of accounts with the given last name")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByLastName(@PathVariable String lastName) {
        log.info("Received GET request for /api/accounts/by-last-name/{}", lastName);
        return ResponseEntity.ok(accountService.getAccountsByLastName(lastName));
    }
}