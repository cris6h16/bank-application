package com.devsu.hackerearth.backend.account.error;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsu.hackerearth.backend.account.error.application.ApplicationErrors;
import com.devsu.hackerearth.backend.account.error.application.ApplicationException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionResolver {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HandledErrorDto> handleException(Exception exception, HttpServletRequest request) {
        HandledErrorDto handledErrorDto = createHandledError(exception, request);
        log.info("HandledErrorDto {}, Exception caught: {}", handledErrorDto, exception);

        return ResponseEntity
                .status(handledErrorDto.getStatus())
                .body(handledErrorDto);
    }

    private HandledErrorDto createHandledError(Exception exception, HttpServletRequest request) {
        String message = exception.getMessage();
        String code = "UNEX000";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof ApplicationException) {
            ApplicationErrors error = ((ApplicationException) exception).getError();
            message = error.getMessage();
            status = getStatus(error);
            code = error.getCode();
        }

        return HandledErrorDto.builder()
                .timestamp(LocalDateTime.now().toString())
                .message(message)
                .status(status)
                .code(code)
                .status(status)
                .path(request.getRequestURI())
                .build();

    }

    private HttpStatus getStatus(ApplicationErrors error) {
        final Map<ApplicationErrors, HttpStatus> handledErrors = Map.of(
                ApplicationErrors.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND,
                ApplicationErrors.TRANSACTION_NOT_FOUND, HttpStatus.NOT_FOUND,
                ApplicationErrors.INVALID_AMOUNT, HttpStatus.BAD_REQUEST,
                ApplicationErrors.INSUFFICIENT_BALANCE, HttpStatus.BAD_REQUEST);

        return handledErrors.get(error);
    }
}
