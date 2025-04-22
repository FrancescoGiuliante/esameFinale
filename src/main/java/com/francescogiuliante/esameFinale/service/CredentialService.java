package com.francescogiuliante.esameFinale.service;

import com.francescogiuliante.esameFinale.dto.request.CredentialDTO;
import com.francescogiuliante.esameFinale.dto.response.CredentialResponseDTO;
import com.francescogiuliante.esameFinale.mapper.CredentialMapper;
import com.francescogiuliante.esameFinale.model.Account;
import com.francescogiuliante.esameFinale.model.Credential;
import com.francescogiuliante.esameFinale.repository.AccountRepository;
import com.francescogiuliante.esameFinale.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private AccountRepository accountRepository;

    public List<CredentialResponseDTO> getAllCredentials() {
        List<Credential> credentials = credentialRepository.findAll();
        List<CredentialResponseDTO> responseDTOs = new ArrayList<>();
        for (Credential credential : credentials) {
            responseDTOs.add(credentialMapper.toResponseDTO(credential));
        }
        return responseDTOs;
    }

    public Optional<CredentialResponseDTO> getCredentialById(Long id) {
        Optional<Credential> credentialOptional = credentialRepository.findById(id);
        if (credentialOptional.isPresent()) {
            return Optional.of(credentialMapper.toResponseDTO(credentialOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    public CredentialResponseDTO createCredential(CredentialDTO credentialDTO) {
        Credential credential = credentialMapper.toEntity(credentialDTO);
        Optional<Account> accountOptional = accountRepository.findById(credentialDTO.getAccountId());
        if (accountOptional.isPresent()) {
            credential.setAccount(accountOptional.get());
            Credential savedCredential = credentialRepository.save(credential);
            return credentialMapper.toResponseDTO(savedCredential);
        }
        return null;
    }

    public Optional<CredentialResponseDTO> updateCredential(Long id, CredentialDTO credentialDTO) {
        Optional<Credential> existingCredentialOptional = credentialRepository.findById(id);
        if (existingCredentialOptional.isPresent()) {
            Credential existingCredential = existingCredentialOptional.get();
            Credential updatedCredential = credentialMapper.toEntity(credentialDTO);
            Optional<Account> accountOptional = accountRepository.findById(credentialDTO.getAccountId());
            if (accountOptional.isPresent()) {
                updatedCredential.setId(id);
                updatedCredential.setAccount(accountOptional.get());
                existingCredential.setEmail(updatedCredential.getEmail());
                existingCredential.setPassword(updatedCredential.getPassword());
                Credential savedCredential = credentialRepository.save(existingCredential);
                return Optional.of(credentialMapper.toResponseDTO(savedCredential));
            }
        }
        return Optional.empty();
    }

    public boolean deleteCredential(Long id) {
        if (credentialRepository.existsById(id)) {
            credentialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<CredentialResponseDTO> getCredentialByEmail(String email) {
        Optional<Credential> credentialOptional = credentialRepository.findByEmail(email);
        if (credentialOptional.isPresent()) {
            return Optional.of(credentialMapper.toResponseDTO(credentialOptional.get()));
        } else {
            return Optional.empty();
        }
    }
}