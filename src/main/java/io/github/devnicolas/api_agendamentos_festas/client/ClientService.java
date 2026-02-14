package io.github.devnicolas.api_agendamentos_festas.client;


import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientResponseDTO;
import io.github.devnicolas.api_agendamentos_festas.client.exceptions.EmailInvalidExeception;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public List<ClientResponseDTO> list(){
    return this.clientRepository.findAll().stream().map(item-> new ClientResponseDTO(item.getName(),item.getEmail(),
    item.getId())).toList();
  }


  @Transactional
  public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) {
    if (clientRequestDTO.email() == null || !clientRequestDTO.email().contains("@"))
      throw new EmailInvalidExeception("Formato de email inválido");

    Client client = new Client();
    client.setName(clientRequestDTO.name());
    client.setEmail(clientRequestDTO.email());
    client.setPassword(clientRequestDTO.password());
    Client clientSaved = clientRepository.save(client);

    return new ClientResponseDTO(clientSaved.getName(), clientSaved.getEmail(), clientSaved.getId());


  }

}
