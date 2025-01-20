package com.devicemanager.app.controller;

import com.devicemanager.app.dto.response.ErrorResponse;
import com.devicemanager.app.exception.*;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@Hidden
public class DeviceControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.internalServerError()
                .body(errorResponse);

    }

    @ExceptionHandler({DeviceNotFoundException.class,
            DeviceStateRequestNotFound.class,
            DeviceStateNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleDeviceNotFoundException(Exception exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler({DeviceInUseException.class,
                        DeviceActivationException.class})
    public ResponseEntity<ErrorResponse> handleDeviceInUseException(Exception ex) {

        log.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);

    }


}
