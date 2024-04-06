package jpa_relationships_school_example.config;

import jpa_relationships_school_example.dtos.ErrorDto;
import jpa_relationships_school_example.exceptions.CustomAppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomAppException.class)
    public ResponseEntity<ErrorDto>  handleException(CustomAppException e){
        ErrorDto errorDto = ErrorDto.builder()
                .message(e.getMessage())
                .httpStatusCode(e.getHttpStatusCode())
                .build();
        return new ResponseEntity<>(errorDto, e.getHttpStatusCode());
    }
}
