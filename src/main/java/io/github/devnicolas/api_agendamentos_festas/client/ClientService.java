package io.github.devnicolas.api_agendamentos_festas.client;


import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService extends BaseServiceImpl<Client, ClientRequestDTO, Long> {


  public ClientService(ClientRepository clientRepository) {
    super(clientRepository);

  }

  @Override
  protected Client toEntity(ClientRequestDTO dto) {
    return new Client(dto.name(), dto.email(), dto.password());
  }

  @Override
  protected void updateEntity(Client entity, ClientRequestDTO dto) {

  }


}
