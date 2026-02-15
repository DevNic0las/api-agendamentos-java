package io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class BaseControllerImpl<T, REQ, RES, ID> implements CrudController<REQ, RES, ID> {

  private final CrudService<T, REQ, ID> service;

  protected abstract RES toResponseDTO(T entity);

  protected BaseControllerImpl(CrudService<T, REQ, ID> service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<RES> create(REQ dtoRequest) {
    T entity = this.service.create(dtoRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(entity));

  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<RES> findById(@PathVariable ID id) {
    return this.service.findById(id).map(this::toResponseDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<List<RES>> findAll() {
    List<RES> entity = this.service.findAll().stream().map(this::toResponseDTO).toList();
    return ResponseEntity.status(HttpStatus.OK).body(entity);
  }

  @Override
  public ResponseEntity<RES> update(ID id, REQ dto) {
    return null;
  }

  @Override
  public ResponseEntity<Void> delete(ID id) {
    this.service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
