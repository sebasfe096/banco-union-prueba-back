package com.bancoUnion.prueba.infrastructure.adapter;

import com.bancoUnion.prueba.domain.models.Client;
import com.bancoUnion.prueba.domain.ports.ClientPersistencePort;
import com.bancoUnion.prueba.infrastructure.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientJpaAdapter implements ClientPersistencePort {

    private final ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Boolean existsByDocumentNumber(String documentNumber) {
        return clientRepository.findByDocumentNumber(documentNumber).isPresent();
    }

    @Override
    public Optional<Client> findByDocumentNumber(String documentType, String documentNumber) {
        return clientRepository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
    }
}
