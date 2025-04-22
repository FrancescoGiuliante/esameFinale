package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.CredentialDTO;
import com.francescogiuliante.esameFinale.dto.response.CredentialResponseDTO;
import com.francescogiuliante.esameFinale.model.Credential;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CredentialMapper {

    CredentialResponseDTO toResponseDTO(Credential credential);

    @Mapping(source = "accountId", target = "account.id")
    Credential toEntity(CredentialDTO credentialDTO);
}