package ch.gab.hourcalculator.api.advice;

import ch.gab.hourcalculator.api.exception.EntityAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<String> entityAlreadyExistsExceptionHandler(EntityAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
