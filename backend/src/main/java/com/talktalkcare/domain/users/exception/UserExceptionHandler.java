package com.talktalkcare.domain.users.exception;

import com.talktalkcare.common.error.ErrorCodeInterface;
import com.talktalkcare.common.response.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(2)
public class UserExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(UserException.class)
    public Api<Void> handleUserException(UserException exception) {
        ErrorCodeInterface errorCode = exception.getErrorCode();

        log.error("UserException occurred: {} - {}", errorCode.getErrorCode(), errorCode.getMessage(), exception);

        return Api.ERROR(exception.getErrorCode().getMessage(), exception.getErrorCode().getErrorCode());
    }

}
