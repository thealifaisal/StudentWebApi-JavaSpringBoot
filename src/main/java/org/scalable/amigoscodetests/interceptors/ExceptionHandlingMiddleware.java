package org.scalable.amigoscodetests.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingMiddleware {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception exception){
        var statusCode = HttpStatus.BAD_REQUEST;
        var errorDto = new ErrorDto(statusCode.toString(), exception.getMessage());
        return new ResponseEntity<ErrorDto>(errorDto, statusCode);
    }
}
