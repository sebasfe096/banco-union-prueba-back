package com.bancoUnion.prueba.api.excepcion;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String idTx;

    public BusinessException(String idTx,String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.idTx = idTx;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getIdTx() {
        return idTx;
    }
}
