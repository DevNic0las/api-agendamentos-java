package io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.CrudService;
import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseControllerImpl<T, REQ, RES, ID> implements CrudController<REQ, RES, ID> {

  private final CrudService<T, REQ, ID> service;

  protected abstract RES toResponseDTO(T entity);

  protected BaseControllerImpl(CrudService<T, REQ, ID> service) {
    this.service = service;
  }

  @PostMapping
  @Override
  public ResponseEntity<RES> create( @RequestBody REQ dtoRequest) {
    T entity = this.service.create(dtoRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(entity));

  }

  @GetMapping("/{id}")
  @Override
  public ResponseEntity<RES> findById(@PathVariable ID id) {
    return this.service.findById(id).map(this::toResponseDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @Override
  public ResponseEntity<List<RES>> findAll() {
    List<RES> entity = this.service.findAll().stream().map(this::toResponseDTO).toList();
    return ResponseEntity.status(HttpStatus.OK).body(entity);
  }

  @PatchMapping("/{id}")
  @Override
  public ResponseEntity<RES> update( @PathVariable ID id, @RequestBody REQ dto) {
    T entity = this.service.update(id, dto);
    return ResponseEntity.status(HttpStatus.OK).body(toResponseDTO(entity));

  }

  @DeleteMapping("/{id}")
  @Override
  public ResponseEntity<Void> delete(@PathVariable ID id) {
    this.service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
