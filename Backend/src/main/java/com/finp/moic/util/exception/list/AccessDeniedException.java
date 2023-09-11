package com.finp.moic.util.exception.list;

import com.finp.moic.util.exception.BusinessException;
import com.finp.moic.util.exception.ExceptionEnum;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(ExceptionEnum e) {
        super(e);
    }
}
