package com.bancoUnion.prueba.domain.service;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import com.bancoUnion.prueba.api.dto.response.ClientResponseDTO;
import com.bancoUnion.prueba.api.dto.response.CreateResponseDTO;

public interface ClientService {

    CreateResponseDTO createClient(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO findClientByIdentity(String documentType, String documentNumber);

    CreateResponseDTO updateClient(ClientRequestDTO clientRequestDTO);
}
