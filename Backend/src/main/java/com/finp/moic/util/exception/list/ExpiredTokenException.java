package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class ExpiredTokenException extends BusinessException {

    public ExpiredTokenException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
