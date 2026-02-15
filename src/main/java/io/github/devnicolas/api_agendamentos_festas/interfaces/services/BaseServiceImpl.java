package io.github.devnicolas.api_agendamentos_festas.interfaces.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, D, ID> implements CrudService<T, D, ID> {
  protected final JpaRepository<T, ID> repository;

  protected BaseServiceImpl(JpaRepository<T, ID> repository) {
    this.repository = repository;
  }

  protected abstract T toEntity(D dto);

  @Override
  public T create(D dtoRequest) {
    T entity = toEntity(dtoRequest);
    return repository.save(entity);
  }

  @Override
  public void delete(ID id) {
    if (!repository.existsById(id)) {
      throw new RuntimeException("Recurso não encontrado com o ID: " + id);
    }
    this.repository.deleteById(id);
  }

  @Override
  public T update(ID id, D dto) {
    return null;
  }

  @Override
  public List<T> findAll() {
    return this.repository.findAll();
  }

  @Override
  public Optional<T> findById(ID id) {
   return this.repository.findById(id);
  }
}
