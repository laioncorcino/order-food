package com.corcino.order_food.application.handler;

import com.corcino.order_food.domain.dto.ErrorResponse;
import com.corcino.order_food.domain.dto.ValidationErrorResponse;
import com.corcino.order_food.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException exception) {
        ErrorResponse error = ErrorResponse.builder()
                .title("Resource Not Found")
                .status(HttpStatus.NOT_FOUND.value())
                .errorMessage(exception.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentException) {
        List<ValidationErrorResponse> validations = new ArrayList<>();
        List<FieldError> fieldErrors = methodArgumentException.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            validations.add(ValidationErrorResponse.builder()
                    .title("Invalid field - Check documentation")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMessage(fieldError.getDefaultMessage())
                    .dateTime(getDateTime())
                    .field(fieldError.getField())
                    .build()
            );

            log.error("There is error in field {} to create or update resource", fieldError.getField());
        });

        return new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
    }

    private LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

}
