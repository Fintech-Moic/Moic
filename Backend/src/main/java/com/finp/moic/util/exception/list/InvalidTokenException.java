package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
