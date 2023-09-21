package com.finp.moic.util.exception;

import com.finp.moic.util.exception.list.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final BusinessException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final ValidationException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final NotFoundException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final AlreadyExistException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    @ExceptionHandler({DeniedException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final DeniedException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        .errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    /* 혜지 : 권한 관련 예외 만들어서 포괄 처리하는 게 가능하지 않을까? */
    @ExceptionHandler({IdOrPasswordNotMatchedException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final IdOrPasswordNotMatchedException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        . errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    /* 혜지 : NotFoundException으로 처리 가능하지 않을까? */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final UserNotFoundException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        . errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    @ExceptionHandler({ExpiredTokenException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final ExpiredTokenException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        . errorMessage(e.getError().getErrorMessage())
                        .build());
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<BusinessExceptionEntity> exceptionHandler(HttpServletRequest request, final InvalidTokenException e){
        return ResponseEntity.status(e.getError().getStatus())
                .body(BusinessExceptionEntity.builder()
                        .errorCode(e.getError().getErrorCode())
                        . errorMessage(e.getError().getErrorMessage())
                        .build());
    }
}
