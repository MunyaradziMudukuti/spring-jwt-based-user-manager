package zw.co.munyasys.common.exceptions.handling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.ServletException;
import jakarta.transaction.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import zw.co.munyasys.common.exceptions.DuplicateResourceException;
import zw.co.munyasys.common.exceptions.InvalidRequestException;
import zw.co.munyasys.common.exceptions.ResourceNotFoundException;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ApiException> handleNotFound(RuntimeException ex) {
        final ApiException apiException = getApiException(ex, HttpStatus.NOT_FOUND);
        return buildResponseEntity(apiException);
    }

    private ApiException getApiException(RuntimeException ex, HttpStatus status) {
        log.error("", ex);
        return new ApiException(
                ex.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.systemDefault())
        );
    }

    @ExceptionHandler(value = {DuplicateResourceException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<ApiException> handleConflict(RuntimeException ex) {
        final ApiException apiException = getApiException(ex, HttpStatus.CONFLICT);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiException> handleBadRequest(RuntimeException ex) {
        final ApiException apiException = getApiException(ex, HttpStatus.BAD_REQUEST);
        return buildResponseEntity(apiException);

    }

    @ExceptionHandler(value = {DateTimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiException> handleInvalidDateFormat(RuntimeException ex) {
        final ApiException apiException = getApiException(ex, HttpStatus.BAD_REQUEST);
        return buildResponseEntity(apiException);

    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiException> handleInvalidRequestBinding(ServletException ex) {
        final ApiException apiException = getApiException(new RuntimeException(ex.getMessage()), HttpStatus.BAD_REQUEST);
        return buildResponseEntity(apiException);

    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiException> handleInvalidFormatRequest(RuntimeException ex) {
        final ApiException apiException = getApiException(ex, HttpStatus.BAD_REQUEST);
        return buildResponseEntity(apiException);

    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiException> handleBadRequest(MethodArgumentNotValidException ex) {
        return buildResponseEntity(new ApiException(
                ex.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(System.getProperty("line.separator"))),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.systemDefault())
        ));

    }

    @ExceptionHandler({SystemException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ApiException> handleInternalServerRequest(RuntimeException ex) {
        final ApiException apiException = getApiException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getHttpStatus());
    }

}
