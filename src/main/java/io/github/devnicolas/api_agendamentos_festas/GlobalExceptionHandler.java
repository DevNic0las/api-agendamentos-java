package io.github.devnicolas.api_agendamentos_festas;

import io.github.devnicolas.api_agendamentos_festas.exception.ErrorResponseException;
import io.github.devnicolas.api_agendamentos_festas.exception.ResourceNotFoundException;
import io.github.devnicolas.api_agendamentos_festas.exception.ValidationException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseException> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseException(HttpStatus.NOT_FOUND.value(),
            ex.getMessage()));
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("statusCode", HttpStatus.BAD_REQUEST.value());
    response.put("message", "Erro de validação");
    response.put("errors", ex.getErrors());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();

    Map<String, Object> body = new HashMap<>();
    body.put("message", "Erro de validação");
    body.put("errors", errors);
    body.put("statusCode", HttpStatus.BAD_REQUEST.value());

    return ResponseEntity.badRequest().body(body);
  }
}
