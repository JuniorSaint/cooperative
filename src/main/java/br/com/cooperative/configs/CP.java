package br.com.cooperative.configs;

public interface CP {
    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String ATTIBUTE_BEARER = "Bearer ";
    public static final Long EXPIRATION = 3600l * 24 * 7;  // this value represent time in seconds


    //    Messages
    public static final String DELETE_MESSAGE = ", was deleted with success.";
    public static final String NOT_FOUND = " not found, verify and try again - ";
}

