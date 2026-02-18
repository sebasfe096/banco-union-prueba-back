package com.bancoUnion.prueba;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import com.bancoUnion.prueba.api.dto.response.ClientResponseDTO;
import com.bancoUnion.prueba.api.dto.response.CreateResponseDTO;
import com.bancoUnion.prueba.api.excepcion.BusinessException;
import com.bancoUnion.prueba.domain.mappers.ClientMapper;
import com.bancoUnion.prueba.domain.models.Client;
import com.bancoUnion.prueba.domain.ports.ClientPersistencePort;
import com.bancoUnion.prueba.domain.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientRequestDTO requestDTO;
    private Client clientEntity;

    @BeforeEach
    void setUp() {
        requestDTO = new ClientRequestDTO();
        requestDTO.setIdTx("TX-123");
        requestDTO.setDocumentType("CC");
        requestDTO.setDocumentNumber("1010");

        clientEntity = new Client();
        clientEntity.setId(1);
        clientEntity.setDocumentNumber("1010");
    }

    @Test
    @DisplayName("Debe crear un cliente exitosamente")
    void createClient_Success() {
        // GIVEN
        when(clientPersistencePort.existsByDocumentNumber(anyString())).thenReturn(false);
        when(clientMapper.toEntity(any())).thenReturn(clientEntity);
        when(clientPersistencePort.save(any())).thenReturn(clientEntity);

        // WHEN
        CreateResponseDTO response = clientService.createClient(requestDTO);

        // THEN
        assertNotNull(response);
        assertEquals("TX-123", response.getIdTx());
        assertTrue(response.getMessage().contains("1010"));
        verify(clientPersistencePort, times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar BusinessException cuando el cliente ya existe")
    void createClient_AlreadyExists_ThrowsException() {
        // GIVEN
        when(clientPersistencePort.existsByDocumentNumber("1010")).thenReturn(true);

        // WHEN & THEN
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            clientService.createClient(requestDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertTrue(exception.getMessage().contains("ya se encuentra registrado"));
        verify(clientPersistencePort, never()).save(any());
    }

    @Test
    @DisplayName("Debe encontrar un cliente por identidad")
    void findClientByIdentity_Success() {
        // GIVEN
        when(clientPersistencePort.findByDocumentNumber("CC", "1010"))
                .thenReturn(Optional.of(clientEntity));
        when(clientMapper.toDTO(clientEntity)).thenReturn(new ClientResponseDTO());

        // WHEN
        ClientResponseDTO response = clientService.findClientByIdentity("CC", "1010");

        // THEN
        assertNotNull(response);
        verify(clientPersistencePort).findByDocumentNumber("CC", "1010");
    }

    @Test
    @DisplayName("Debe lanzar error 404 cuando el cliente no existe")
    void findClientByIdentity_NotFound() {
        // GIVEN
        when(clientPersistencePort.findByDocumentNumber("CC", "1010"))
                .thenReturn(Optional.empty());

        // WHEN & THEN
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            clientService.findClientByIdentity("CC", "1010");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
