package dev.decagon.Activity_Tracker.exceptions;


import dev.decagon.Activity_Tracker.utils.GlobalErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<GlobalErrorMessage> handleNotFoundException(ResourceNotFoundException ex){

        GlobalErrorMessage errorMsg = new GlobalErrorMessage();
        errorMsg.setMessage(ex.getMessage());
        errorMsg.setDebugMessage(ex.getDebugMessage());
        errorMsg.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<GlobalErrorMessage> handleNullPointer(NullUserException ex){

        GlobalErrorMessage errorMsg = new GlobalErrorMessage();
        errorMsg.setMessage(ex.getMessage());
        errorMsg.setDebugMessage(ex.getDebugMsg());
        errorMsg.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity(errorMsg, HttpStatus.NOT_FOUND);
    }


}



