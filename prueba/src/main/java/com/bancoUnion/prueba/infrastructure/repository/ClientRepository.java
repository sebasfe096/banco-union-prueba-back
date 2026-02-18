package com.bancoUnion.prueba.infrastructure.repository;

import com.bancoUnion.prueba.domain.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByDocumentNumber(String documentNumber);

    Optional<Client> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
}
