package com.francescogiuliante.esameFinale.mapper;

import com.francescogiuliante.esameFinale.dto.request.AccountDTO;
import com.francescogiuliante.esameFinale.dto.response.AccountResponseDTO;
import com.francescogiuliante.esameFinale.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponseDTO toResponseDTO(Account account);

    Account toEntity(AccountDTO accountDTO);
}