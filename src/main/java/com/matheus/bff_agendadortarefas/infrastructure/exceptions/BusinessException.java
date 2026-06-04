package com.matheus.bff_agendadortarefas.infrastructure.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Throwable throwable){
        super(message, throwable);
    }

}
