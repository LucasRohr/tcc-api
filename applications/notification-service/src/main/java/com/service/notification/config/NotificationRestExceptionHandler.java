package com.service.notification.config;

import com.service.common.domain.CustomErrorRepresentation;
import com.service.common.exceptions.BadRequestException;
import com.service.common.exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotificationRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<CustomErrorRepresentation> handleNotFoundException(
            NotFoundException exception, WebRequest request
    ) {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorRepresentation> handleUnexpectedException(Exception exception) {
        CustomErrorRepresentation response =
                buildErrorRepresentation(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CustomErrorRepresentation> handleBadRequestException(
            BadRequestException exception, WebRequest request
    ) {
        final String message = exception.getMessage();

        CustomErrorRepresentation response = buildErrorRepresentation(HttpStatus.BAD_REQUEST, message);

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    private CustomErrorRepresentation buildErrorRepresentation(final HttpStatus httpStatus, final String message) {
        return CustomErrorRepresentation.builder()
                .message(message)
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

}