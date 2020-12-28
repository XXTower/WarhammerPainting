package fr.bonneau.warhammerPainting.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.exception.UserNotFoundException;


@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(UserNotFoundException e) {

        return ResponseEntity.notFound().build();

    }
    
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleMethodAlreadyExistException(AlreadyExistException e) {
    	return ResponseEntity.badRequest().build();
    }
}
