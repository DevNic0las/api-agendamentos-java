package io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudController<REQ, RES, ID> {

  ResponseEntity<RES> create(REQ dtoRequest);

  ResponseEntity<RES> findById(ID id);

  ResponseEntity<List<RES>> findAll();

  ResponseEntity<RES> update(ID id, REQ dto);

  ResponseEntity<Void> delete(ID id);
}
