package com.finp.moic.util.exception.list;


import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(ExceptionEnum exceptionEnum){
        super(exceptionEnum);
    }
}
