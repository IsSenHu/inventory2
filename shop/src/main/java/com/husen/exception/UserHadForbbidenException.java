package com.husen.exception;

import org.apache.shiro.authc.AuthenticationException;

public class UserHadForbbidenException extends AuthenticationException {
    public UserHadForbbidenException() {
        super();
    }
    public UserHadForbbidenException(String message) {
        super(message);
    }
}
