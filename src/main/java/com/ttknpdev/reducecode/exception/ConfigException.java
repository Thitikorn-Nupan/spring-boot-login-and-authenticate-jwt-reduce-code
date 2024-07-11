/**
package com.ttknpdev.reducecode.exception;

import com.ttknpdev.reducecode.entities.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
// Optional in this project
@ControllerAdvice // @ControllerAdvice marks the class as a global exception handler, allowing it to handle exceptions from multiple controllers.
public class ConfigException {
    // this case not working on JwtFilter but work for UserService
    @ExceptionHandler(TokenExpiredNotAllowed.class)
    public ResponseEntity getTokenExpiredNotAllowed(TokenExpiredNotAllowed tokenExpiredNotAllowed){
        // System.out.println("getTokenExpiredNotAllowed works");
        ResponseException responseException = new ResponseException((short) HttpStatus.METHOD_NOT_ALLOWED.value(),tokenExpiredNotAllowed.getMessage());
        return new ResponseEntity<ResponseException>(responseException,HttpStatus.METHOD_NOT_ALLOWED);
    }
}*/
