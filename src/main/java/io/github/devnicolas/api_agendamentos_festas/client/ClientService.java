package io.github.devnicolas.api_agendamentos_festas.client;


import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.BaseServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService extends BaseServiceImpl<Client, ClientRequestDTO, Long> {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public ClientService(ClientRepository clientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    super(clientRepository);
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  protected Client toEntity(ClientRequestDTO dto) {
    return new Client(dto.name(),dto.numberOfPhone(),dto.dateOfBirth());
  }

  @Override
  protected void updateEntity(Client entity, ClientRequestDTO dto) {
    entity.setName(dto.name());
    entity.setPhoneNumber(dto.numberOfPhone());
    entity.setDateOfBirth(dto.dateOfBirth());
  }


}
