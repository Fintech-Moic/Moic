package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class IdOrPasswordNotMatchedException extends BusinessException {
    public IdOrPasswordNotMatchedException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
