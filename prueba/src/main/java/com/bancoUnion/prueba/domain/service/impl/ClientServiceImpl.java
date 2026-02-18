package com.bancoUnion.prueba.domain.service.impl;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import com.bancoUnion.prueba.api.dto.response.ClientResponseDTO;
import com.bancoUnion.prueba.api.dto.response.CreateResponseDTO;
import com.bancoUnion.prueba.api.excepcion.BusinessException;
import com.bancoUnion.prueba.domain.mappers.ClientMapper;
import com.bancoUnion.prueba.domain.models.Client;
import com.bancoUnion.prueba.domain.ports.ClientPersistencePort;
import com.bancoUnion.prueba.domain.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientPersistencePort clientPersistencePort;

    private final ClientMapper clientMapper;

    @Override
    public CreateResponseDTO createClient(ClientRequestDTO request) {
        final String idTx = request.getIdTx();
        log.info("[TransactionID: {}] Iniciando proceso de creación para cliente: {} {}",
                idTx, request.getDocumentType(), request.getDocumentNumber());

        validateClientExistence(request);

        Client clientToSave = clientMapper.toEntity(request);
        Client savedClient = clientPersistencePort.save(clientToSave);

        log.info("[TransactionID: {}] Cliente persistido exitosamente con ID interno: {}", idTx, savedClient.getId());

        return buildSuccessResponse(idTx, request.getDocumentNumber());
    }

    @Override
    public ClientResponseDTO findClientByIdentity(String documentType, String documentNumber) {
        log.info("[Consult] Iniciando búsqueda de cliente: {} - {}", documentType, documentNumber);

        return clientPersistencePort.findByDocumentNumber(documentType, documentNumber)
                .map(clientMapper::toDTO)
                .orElseThrow(() -> handleClientNotFound(documentType, documentNumber));
    }

    @Override
    @Transactional
    public CreateResponseDTO updateClient(ClientRequestDTO request) {
        log.info("[TransactionID: {}] Iniciando proceso de actualización para cliente: {} {}",
                request.getIdTx(), request.getDocumentType(), request.getDocumentNumber());

        Client clientDb = getClient(request.getDocumentType(), request.getDocumentNumber());

        clientDb.updateFrom(request);

        clientPersistencePort.save(clientDb);

        log.info("[TransactionID: {}] Actualización exitosa para cliente: {}", request.getIdTx(), request.getDocumentNumber());

        return buildSuccessResponse(request.getIdTx(), request.getDocumentNumber());
    }

    private Client getClient(String documentType, String documentNumber) {
        return clientPersistencePort.findByDocumentNumber(documentType, documentNumber).orElseThrow(() -> handleClientNotFound(documentType, documentNumber));
    }

    private BusinessException handleClientNotFound(String documentType, String documentNumber) {
        String errorMessage = String.format("Cliente %s %s no se encuentra registrado",
                documentType, documentNumber);

        log.warn("[Consult-Error] Búsqueda fallida: {}", errorMessage);

        return new BusinessException("", errorMessage, HttpStatus.NOT_FOUND);
    }

    private void validateClientExistence(ClientRequestDTO request) {
        if (Boolean.TRUE.equals(clientPersistencePort.existsByDocumentNumber(request.getDocumentNumber()))) {
            String errorMessage = String.format("Cliente %s %s ya se encuentra registrado",
                    request.getDocumentType(),
                    request.getDocumentNumber());

            log.warn("[TransactionID: {}] Intento de registro duplicado: {}", request.getIdTx(), errorMessage);

            throw new BusinessException(request.getIdTx(), errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    private CreateResponseDTO buildSuccessResponse(String idTx, String documentNumber) {
        String successMessage = String.format("Cliente %s almacenado de forma exitosa", documentNumber);
        return CreateResponseDTO.builder()
                .idTx(idTx)
                .message(successMessage)
                .build();
    }
}
