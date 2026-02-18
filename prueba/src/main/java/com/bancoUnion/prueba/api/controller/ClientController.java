package com.bancoUnion.prueba.api.controller;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import com.bancoUnion.prueba.api.dto.response.ClientResponseDTO;
import com.bancoUnion.prueba.api.dto.response.CreateResponseDTO;
import com.bancoUnion.prueba.domain.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/guardarCliente")
    public ResponseEntity<CreateResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        return ResponseEntity.ok(clientService.createClient(clientRequestDTO));
    }

    @PostMapping("/actualizarCliente")
    public ResponseEntity<CreateResponseDTO> updateClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        return ResponseEntity.ok(clientService.updateClient(clientRequestDTO));
    }

    @GetMapping("/consultarCliente/{documentType}_{documentNumber}")
    public ResponseEntity<ClientResponseDTO> consultClient(@PathVariable String documentType, @PathVariable String documentNumber) {
        return ResponseEntity.ok(clientService.findClientByIdentity(documentType, documentNumber));
    }
}
