package io.github.devnicolas.api_agendamentos_festas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponseException {
  public Map<String, Object> response;

  public ErrorResponseException(int statusCode, String message) {
    this.response = new HashMap<>();
    response.put("statusCode", statusCode);
    response.put("message", message);


  }
}
