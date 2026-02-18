package com.bancoUnion.prueba.domain.mappers;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import com.bancoUnion.prueba.api.dto.response.ClientResponseDTO;
import com.bancoUnion.prueba.domain.models.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        if (dto == null) return null;

        Client client = new Client();
        client.setDocumentType(dto.getDocumentType());
        client.setDocumentNumber(dto.getDocumentNumber());
        client.setFirstName(dto.getFirstName());
        client.setSecondName(dto.getSecondName());
        client.setLastName(dto.getLastName());
        client.setSecondLastName(dto.getSecondLastName());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setEmail(dto.getEmail());

        return client;
    }

    public ClientResponseDTO toDTO(Client client) {
        if (client == null) return null;

        ClientResponseDTO dto = new ClientResponseDTO();

        dto.setDocumentType(client.getDocumentType());
        dto.setDocumentNumber(client.getDocumentNumber());
        dto.setFirstName(client.getFirstName());
        dto.setSecondName(client.getSecondName());
        dto.setLastName(client.getLastName());
        dto.setSecondLastName(client.getSecondLastName());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setEmail(client.getEmail());

        return dto;
    }
}
