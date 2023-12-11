package com.noteu.noteu.member.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistAuthenticationException extends RuntimeException {
    public UserAlreadyExistAuthenticationException(String message) {
        super(message);
    }


}
