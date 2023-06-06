package zw.co.munyasys.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<ErrorMessage> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleRecordNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public final ResponseEntity<ErrorMessage> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<ErrorMessage> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public final ResponseEntity<ErrorMessage> handleIllegalAccessException(IllegalAccessException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalOperationException.class)
    public final ResponseEntity<ErrorMessage> handleIllegalOperationException(IllegalOperationException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<ErrorMessage> handleIllegalOperationException(NullPointerException ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleIException(Exception ex, WebRequest request) {
        ErrorMessage errorDetails = new ErrorMessage(new Date(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
