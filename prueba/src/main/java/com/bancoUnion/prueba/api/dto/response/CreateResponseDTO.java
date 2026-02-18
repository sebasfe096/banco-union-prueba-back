package com.bancoUnion.prueba.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResponseDTO {

    private String idTx;

    @JsonProperty("mensaje")
    private String message;
}
