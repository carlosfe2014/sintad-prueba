package net.carlosfe.tests.sintad.global.exceptions;

import io.jsonwebtoken.JwtException;
import net.carlosfe.tests.sintad.global.models.CustomFieldError;
import net.carlosfe.tests.sintad.global.models.ErrorResponse;
import net.carlosfe.tests.sintad.global.utils.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageUtil messageUtil;

    public GlobalExceptionHandler(final MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }



    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> badCredentialsExceptionHandler(BadCredentialsException ex, HttpServletRequest request) {
        final String localizedMessage = this.messageUtil.getMessage(ex.getClass().getSimpleName() + ".message");
        logger.info(localizedMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(localizedMessage, ex.getMessage()));
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<Object> jwtExceptionHandler(JwtException ex, HttpServletRequest request) {
        final String localizedMessage = this.messageUtil.getMessage(ex.getClass().getSimpleName() + ".message");
        logger.info(localizedMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(localizedMessage, ex.getMessage()));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        final String localizedMessage = this.messageUtil.getMessage(ex.getClass().getSimpleName() + ".message");
        logger.info(localizedMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(localizedMessage, ex.getMessage()));
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        final String localizedMessage = this.messageUtil.getMessage(ex.getMessage());
        logger.info(localizedMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(localizedMessage));
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<org.springframework.validation.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<CustomFieldError> errorsMap =  fieldErrors.stream().map((fieldError) -> new CustomFieldError(fieldError.getField(), this.messageUtil.getMessageError(fieldError))).collect(Collectors.toList());
        final String localizedMessage = this.messageUtil.getMessage(ex.getClass().getSimpleName() + ".message");
        String message = (localizedMessage != null || !localizedMessage.isEmpty() ? localizedMessage : status.getReasonPhrase());
        logger.info(message);
        return new ResponseEntity(new ErrorResponse(message, ex.getMessage(), errorsMap), headers, status);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String localizedMessage = this.messageUtil.getMessage(ex.getClass().getSimpleName() + ".message");
        String message = (localizedMessage != null || !localizedMessage.isEmpty() ? localizedMessage : status.getReasonPhrase());
        logger.info(message);
        return super.handleExceptionInternal(ex, new ErrorResponse(message, ex.getMessage()), headers, status, request);
    }
}
