package db.coursework.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class, EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleDataNotFound(RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, IllegalArgumentException.class, PropertyValueException.class, PropertyReferenceException.class, DataIntegrityViolationException.class, ArrayIndexOutOfBoundsException.class, InvalidDataAccessApiUsageException.class})
    protected ResponseEntity<Object> handleDataIncorrect(RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleInputIncorrect(RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, "Invalid value supplied", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleServer(RuntimeException e, WebRequest request) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleExceptionInternal(ex, "Missing path variable", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleExceptionInternal(ex, "Missing request parameter", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return this.handleExceptionInternal(ex, "Invalid data supplied", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
