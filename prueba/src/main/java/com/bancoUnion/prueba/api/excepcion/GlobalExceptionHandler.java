package com.bancoUnion.prueba.api.excepcion;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("ingresa handleValidationExceptions");
       String idTx = "N/A";
        if (ex.getBindingResult().getTarget() instanceof ClientRequestDTO dto) {
            idTx = dto.getIdTx();
        }

        List<String> mandatory = new ArrayList<>();
        List<String> invalidFormat = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField();
            String code = error.getCode();

            if ("NotBlank".equals(code) || "NotNull".equals(code)) {
                mandatory.add(fieldName);
            } else if ("Email".equals(code)) {
                invalidFormat.add(fieldName);
            } else {
                // Para otros como Size o Pattern
                invalidFormat.add(fieldName);
            }
        }

        StringBuilder messageBuilder = new StringBuilder();

        if (!mandatory.isEmpty()) {
            messageBuilder.append("Campos ")
                    .append(String.join(", ", mandatory))
                    .append(". Son obligatorios. ");
        }

        if (!invalidFormat.isEmpty()) {
            messageBuilder.append("Campo ")
                    .append(String.join(", ", invalidFormat))
                    .append(", no cumple con la estructura de un correo electrónico valido.");
        }

        CustomErrorResponse errorBody = new CustomErrorResponse(idTx, messageBuilder.toString().trim());

        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomErrorResponse> handleBusinessExceptions(BusinessException ex) {
        log.error("Error de negocio: {} - Status: {}", ex.getMessage(), ex.getHttpStatus().value());

        CustomErrorResponse errorBody = new CustomErrorResponse(ex.getIdTx().equalsIgnoreCase("") ? null : ex.getIdTx(),
                ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus().value()).body(errorBody);
    }
}
