package com.fantasticfour.shareyourrecipes.exception;
import org.springframework.security.core.AuthenticationException;

public class LocalAuthenticationFailException extends AuthenticationException {
    public LocalAuthenticationFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public LocalAuthenticationFailException(String msg) {
        super(msg);
    }
}