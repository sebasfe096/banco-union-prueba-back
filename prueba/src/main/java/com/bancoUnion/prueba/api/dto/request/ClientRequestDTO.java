package com.bancoUnion.prueba.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "El ID de transacción es obligatorio")
    private String idTx;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Size(min = 2, max = 5, message = "El tipo de documento debe tener entre 2 y 5 caracteres")
    @JsonProperty("tipoDocumento")
    private String documentType;

    @NotBlank(message = "El número de documento es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de documento debe ser numérico")
    @JsonProperty("numeroDocumento")
    private String documentNumber;

    @NotBlank(message = "El primer nombre es obligatorio")
    @JsonProperty("primerNombre")
    private String firstName;

    @JsonProperty("segundoNombre")
    private String secondName;

    @NotBlank(message = "El primer apellido es obligatorio")
    @JsonProperty("primerApellido")
    private String lastName;

    @JsonProperty("segundoApellido") // Opcional
    private String secondLastName;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 7, max = 15)
    @JsonProperty("teléfono")
    private String phoneNumber;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo es inválido")
    @JsonProperty("correoElectronico")
    private String email;
}
