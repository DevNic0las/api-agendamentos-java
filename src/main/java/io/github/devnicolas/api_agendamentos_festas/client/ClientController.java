package io.github.devnicolas.api_agendamentos_festas.client;


import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping

  public ResponseEntity<List<ClientResponseDTO>> list(){
    List<ClientResponseDTO> list = this.clientService.list();
    return ResponseEntity.ok().body(list);
  }


  @PostMapping
  public ResponseEntity<ClientResponseDTO> create(@RequestBody ClientRequestDTO dtoRequest){
    ClientResponseDTO response = clientService.create(dtoRequest);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();


    return ResponseEntity.created(uri).body(response);
  }
}
