package com.finp.moic.util.exception;

public class IdOrPasswordNotMatchedException extends BusinessException {
    public IdOrPasswordNotMatchedException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
