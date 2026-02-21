package io.github.devnicolas.api_agendamentos_festas.client;

import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientResponseDTO;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers.BaseControllerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Gerenciamento de clientes")
public class ClientController extends BaseControllerImpl<Client, ClientRequestDTO, ClientResponseDTO, Long> {

  public ClientController(ClientService clientService) {
    super(clientService);

  }

  @Override
  @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com nome, telefone e data de nascimento")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos (name vazio, telefone inválido, dob nulo)"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<ClientResponseDTO> create(@RequestBody ClientRequestDTO dtoRequest) {
    return super.create(dtoRequest);
  }

  @Override
  @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente encontrado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
    return super.findById(id);
  }

  @Override
  @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<java.util.List<ClientResponseDTO>> findAll() {
    return super.findAll();
  }

  @Override
  @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente (nome, telefone, data de nascimento)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @RequestBody ClientRequestDTO dto) {
    return super.update(id, dto);
  }

  @Override
  @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return super.delete(id);
  }

  @Override
  protected ClientResponseDTO toResponseDTO(Client entity) {
    return new ClientResponseDTO(entity.getName(), entity.getPhoneNumber(), entity.getDateOfBirth(),entity.getId());
  }
}
