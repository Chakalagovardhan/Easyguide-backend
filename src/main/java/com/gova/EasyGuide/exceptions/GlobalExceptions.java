package com.gova.EasyGuide.exceptions;


import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {


    @ExceptionHandler(AllExceptions.userAllReadyExist.class)
    public ResponseEntity<String> userallreadyPresent(AllExceptions.userAllReadyExist ex)
    {
        return  new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AllExceptions.courseAllReadyExist.class)
    public ResponseEntity<String> courseExistWithName(AllExceptions.courseAllReadyExist ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

}
