package com.goylik.planner.controller.advice;

import com.goylik.planner.model.dto.ErrorResponse;
import com.goylik.planner.service.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PlannerControllerAdvice {
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleBookNotFoundException(EmployeeNotFoundException ex) {
        log.error("NOT FOUND: {}", ex.getMessage(), ex);
        return new ErrorResponse("Not Found", 404, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ErrorResponse handleDataAccessException(DataAccessException ex) {
        log.error("DATA ACCESS ERROR: {}", ex.getMessage(), ex);
        return new ErrorResponse("Internal Server Error", 500,
                "An error occurred while accessing the data: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error("INTERNAL SERVER ERROR: {}", ex.getMessage(), ex);
        return new ErrorResponse("Internal Server Error", 500, ex.getMessage());
    }
}
