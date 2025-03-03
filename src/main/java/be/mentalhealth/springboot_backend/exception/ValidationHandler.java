package be.mentalhealth.springboot_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidation(MethodArgumentNotValidException exception) {

        String message = "";

        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()) {
            message += fieldError.getDefaultMessage() + "\n";
        }

        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}
