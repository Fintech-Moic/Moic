package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class ReLoginException extends BusinessException {

    public ReLoginException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
