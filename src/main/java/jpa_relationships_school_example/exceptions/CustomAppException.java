package jpa_relationships_school_example.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CustomAppException extends RuntimeException{


    private HttpStatus httpStatusCode;

    public CustomAppException(String message, HttpStatus httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }


}
