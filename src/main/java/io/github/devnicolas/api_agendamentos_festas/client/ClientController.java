package io.github.devnicolas.api_agendamentos_festas.client;

import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientResponseDTO;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.CrudService;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers.BaseControllerImpl;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clients")
public class ClientController extends BaseControllerImpl<Client, ClientRequestDTO, ClientResponseDTO, Long> {

  public ClientController(ClientService clientService) {
    super(clientService);

  }

  @Override
  protected ClientResponseDTO toResponseDTO(Client entity) {
    return new ClientResponseDTO(entity.getName(), entity.getPhoneNumber(), entity.getDateOfBirth(),entity.getId());
  }
}
