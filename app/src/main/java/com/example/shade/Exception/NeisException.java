package com.example.shade.Exception;

public class NeisException extends RuntimeException{
    public NeisException(){
        super();
    }

    public NeisException(String message){
        super(message);
    }

    public NeisException(String message, Throwable cause){
        super(message, cause);
    }
}
