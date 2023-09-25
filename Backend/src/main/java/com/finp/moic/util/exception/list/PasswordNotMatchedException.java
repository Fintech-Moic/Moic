package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class PasswordNotMatchedException extends BusinessException {
    public PasswordNotMatchedException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
