package zw.co.munyasys.common.exceptions.handling;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {

    private final HttpStatus httpStatus;
    private final String message;
    private final ZonedDateTime timestamp;
    private int code;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.timestamp = timestamp;
    }
}
