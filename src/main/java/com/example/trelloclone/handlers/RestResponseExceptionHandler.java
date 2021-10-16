package com.example.trelloclone.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final DefaultErrorAttributes defaultErrorAttributes;

    @Autowired
    public RestResponseExceptionHandler(DefaultErrorAttributes defaultErrorAttributes) {
        this.defaultErrorAttributes = defaultErrorAttributes;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleUsernameNotFoundException(
            RuntimeException exception,
            WebRequest request
    ) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value(), RequestAttributes.SCOPE_REQUEST);
        Map<String, Object> errorAttributes = defaultErrorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)
        );
        return handleExceptionInternal(exception, errorAttributes, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<Object> handleResponseStatusException(
            RuntimeException exception,
            WebRequest request
    ) {
        HttpStatus httpStatus = HttpStatus.valueOf(
                Integer.parseInt(exception.toString().replaceAll("\\D+", ""))
        );
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, httpStatus.value(), RequestAttributes.SCOPE_REQUEST);
        Map<String, Object> errorAttributes = defaultErrorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        String message = exception.getMessage();
        errorAttributes.put("message", message.substring(message.indexOf('"') + 1, message.length() - 1));

        return handleExceptionInternal(exception, errorAttributes, new HttpHeaders(), httpStatus, request);
    }

}