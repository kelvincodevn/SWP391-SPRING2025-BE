package be.mentalhealth.springboot_backend.exception;

import be.mentalhealth.springboot_backend.exception.exceptions.AuthorizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class APIHandleException {

    //mỗi khi có lỗi validation thì chạy xử lý này

    //MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBadRequestException(MethodArgumentNotValidException exception){
        String message = "";

        for(FieldError error: exception.getBindingResult().getFieldErrors()){
            message+=error.getDefaultMessage() + "\n";
        }

        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleDuplicateException(SQLIntegrityConstraintViolationException exception){
        return new ResponseEntity("duplicate", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointer(NullPointerException exception){
        return new ResponseEntity(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity handleAuthenticateException(AuthorizeException exception){
        return new ResponseEntity(exception.getMessage(),HttpStatus.UNAUTHORIZED);
    }
}
