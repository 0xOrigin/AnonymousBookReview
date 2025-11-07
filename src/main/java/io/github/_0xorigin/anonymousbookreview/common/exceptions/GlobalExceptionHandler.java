package io.github._0xorigin.anonymousbookreview.common.exceptions;

import io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse.ApiErrorResponse;
import io.github._0xorigin.anonymousbookreview.common.responses.apierrorresponse.StandardApiErrorResponse;
import io.github._0xorigin.queryfilterbuilder.base.services.LocalizationService;
import io.github._0xorigin.queryfilterbuilder.exceptions.QueryBuilderConfigurationException;
import io.github._0xorigin.queryfilterbuilder.exceptions.QueryFilterBuilderExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends QueryFilterBuilderExceptionHandler {

    @Value("${core-crud.exceptions.show-stacktrace:false}")
    private boolean showStackTrace;
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource, LocalizationService localizationService) {
        super(localizationService);
        this.messageSource = messageSource;
    }

    protected String getRequestPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(@NotNull MethodArgumentNotValidException e, WebRequest request, Locale locale) {
        Map<String, List<String>> groupedErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                "Validation failed",
                getRequestPath(request),
                groupedErrors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

//    @ExceptionHandler(InvalidQueryParameterException.class)
//    @Override
//    public ResponseEntity<?> handleException(@NotNull InvalidQueryParameterException e, WebRequest request) {
//        Map<String, List<String>> groupedErrors = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(Collectors.groupingBy(
//                        FieldError::getField,
//                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
//                ));
//
//        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
//                "Query strings validation failed",
//                getRequestPath(request),
//                groupedErrors
//        );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }

//    @ExceptionHandler(QueryBuilderConfigurationException.class)
    @Override
    public ResponseEntity<?> handleException(@NotNull QueryBuilderConfigurationException e, WebRequest request) {
        Map<String, List<String>> groupedErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                "Query strings configuration failed",
                getRequestPath(request),
                groupedErrors
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleException(@NotNull ValidationException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(@NotNull EntityNotFoundException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(@NotNull DataIntegrityViolationException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleException(@NotNull NoResourceFoundException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<?> handleException(@NotNull MethodNotAllowedException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                "Method \"" + e.getHttpMethod() + "\" is not allowed.",
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handleException(@NotNull PropertyReferenceException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException(@NotNull BadCredentialsException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleException(@NotNull AuthorizationDeniedException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleException(@NotNull HttpMessageNotReadableException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleException(@NotNull ResourceNotFoundException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> handleException(@NotNull BusinessLogicException e, WebRequest request, Locale locale) {
        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getMessage(),
                getRequestPath(request),
                new HashMap<>()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NotNull Exception e, WebRequest request, Locale locale) {
        Map<String, List<String>> errors = new HashMap<>();
        if (showStackTrace) {
            errors.put("exceptionClassName", List.of(e.getClass().getSimpleName()));
            errors.put("stackTrace", Arrays.stream(e.getStackTrace()).map(Object::toString).toList());
        }

        ApiErrorResponse errorResponse = new StandardApiErrorResponse(
                e.getLocalizedMessage(),
                getRequestPath(request),
                errors
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
