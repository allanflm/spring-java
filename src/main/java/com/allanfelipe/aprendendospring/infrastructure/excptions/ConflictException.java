package com.allanfelipe.aprendendospring.infrastructure.excptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
    public ConflictException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
