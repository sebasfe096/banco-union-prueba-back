package com.bancoUnion.prueba.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {

    @JsonProperty("tipoDocumento")
    private String documentType;

    @JsonProperty("numeroDocumento")
    private String documentNumber;

    @JsonProperty("primerNombre")
    private String firstName;

    @JsonProperty("segundoNombre")
    private String secondName;

    @JsonProperty("primerApellido")
    private String lastName;

    @JsonProperty("segundoApellido")
    private String secondLastName;

    @JsonProperty("teléfono")
    private String phoneNumber;

    @JsonProperty("correoElectronico")
    private String email;
}
