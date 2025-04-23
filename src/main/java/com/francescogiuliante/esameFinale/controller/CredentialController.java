package com.francescogiuliante.esameFinale.controller;

import com.francescogiuliante.esameFinale.dto.request.CredentialDTO;
import com.francescogiuliante.esameFinale.dto.response.CredentialResponseDTO;
import com.francescogiuliante.esameFinale.service.CredentialService;
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
@RequestMapping("/api/credentials")
@Tag(name = "Credential", description = "Operations related to user credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @GetMapping
    @Operation(summary = "Get all credentials")
    @ApiResponse(responseCode = "200", description = "List of all credentials")
    public ResponseEntity<List<CredentialResponseDTO>> getAllCredentials() {
        log.info("Received GET request for /api/credentials");
        return ResponseEntity.ok(credentialService.getAllCredentials());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get credential by ID")
    @ApiResponse(responseCode = "200", description = "Credential found")
    @ApiResponse(responseCode = "404", description = "Credential not found")
    public ResponseEntity<CredentialResponseDTO> getCredentialById(@PathVariable Long id) {
        log.info("Received GET request for /api/credentials/{}", id);
        Optional<CredentialResponseDTO> credential = credentialService.getCredentialById(id);
        return credential.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new credential")
    @ApiResponse(responseCode = "201", description = "Credential created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CredentialResponseDTO> createCredential(@RequestBody CredentialDTO credentialDTO) {
        log.info("Received POST request for /api/credentials with body: {}", credentialDTO);
        CredentialResponseDTO createdCredential = credentialService.createCredential(credentialDTO);
        if (createdCredential != null) {
            return new ResponseEntity<>(createdCredential, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing credential")
    @ApiResponse(responseCode = "200", description = "Credential updated")
    @ApiResponse(responseCode = "404", description = "Credential not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<CredentialResponseDTO> updateCredential(@PathVariable Long id, @RequestBody CredentialDTO credentialDTO) {
        log.info("Received PUT request for /api/credentials/{} with body: {}", id, credentialDTO);
        Optional<CredentialResponseDTO> updatedCredential = credentialService.updateCredential(id, credentialDTO);
        return updatedCredential.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a credential by ID")
    @ApiResponse(responseCode = "204", description = "Credential deleted")
    @ApiResponse(responseCode = "404", description = "Credential not found")
    public ResponseEntity<Void> deleteCredential(@PathVariable Long id) {
        log.warn("Received DELETE request for /api/credentials/{}", id);
        if (credentialService.deleteCredential(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get credential by email")
    @ApiResponse(responseCode = "200", description = "Credential found")
    @ApiResponse(responseCode = "404", description = "Credential not found")
    public ResponseEntity<CredentialResponseDTO> getCredentialByEmail(@PathVariable String email) {
        log.info("Received GET request for /api/credentials/by-email/{}", email);
        Optional<CredentialResponseDTO> credential = credentialService.getCredentialByEmail(email);
        return credential.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}