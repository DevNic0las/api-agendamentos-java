package io.github.devnicolas.api_agendamentos_festas.interfaces.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, D, ID> {

  T create(D dtoRequest);

  Optional<T> findById(ID id);

  List<T> findAll();

  T update(ID id, D dto);

  void delete(ID id);


}
