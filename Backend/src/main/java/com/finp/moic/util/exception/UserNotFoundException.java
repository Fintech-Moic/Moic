package com.finp.moic.util.exception;


public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
