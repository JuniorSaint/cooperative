package br.com.cooperative.exceptions;

import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAuthorizateException   {
    private static final long serialVersionUID = 1L;

//    public NotAuthorizateException(String message){
//        super(message);
//    }
}


