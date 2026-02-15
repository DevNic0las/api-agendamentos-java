package io.github.devnicolas.api_agendamentos_festas.client;

import io.github.devnicolas.api_agendamentos_festas.client.exceptions.ClientNotFoundException;
import io.github.devnicolas.api_agendamentos_festas.client.exceptions.EmailInvalidExeception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EmailInvalidExeception.class)
  public ResponseEntity<String> EmailInvalidException(EmailInvalidExeception ex){
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
  @ExceptionHandler(ClientNotFoundException.class)
  public ResponseEntity<String> clientNotFoundException(ClientNotFoundException ex){
    return ResponseEntity.internalServerError().body(ex.getMessage());
  }

}
