package com.bancoUnion.prueba.domain.ports;

import com.bancoUnion.prueba.domain.models.Client;

import java.util.Optional;

public interface ClientPersistencePort {

    Client save(Client client);

    Boolean existsByDocumentNumber(String documentNumber);

    Optional<Client> findByDocumentNumber(String documentType, String documentNumber);
}
