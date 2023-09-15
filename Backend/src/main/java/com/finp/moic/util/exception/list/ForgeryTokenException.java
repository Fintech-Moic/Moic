package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class ForgeryTokenException extends BusinessException {

    public ForgeryTokenException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
