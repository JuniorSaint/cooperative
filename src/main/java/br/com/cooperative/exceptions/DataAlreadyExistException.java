package br.com.cooperative.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataAlreadyExistException(String message) {
        super(message);
    }
}

