package io.github.devnicolas.api_agendamentos_festas.place;

import io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers.BaseControllerImpl;
import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@Tag(name = "Places", description = "Gerenciamento de espaços para festas")
public class PlaceController extends BaseControllerImpl<Place, PlaceRequestDTO, PlaceResponseDTO, Long> {

  public PlaceController(PlaceService placeService) {
    super(placeService);
  }

  @Override
  @Operation(summary = "Criar novo espaço", description = "Cria um novo espaço para festas com nome, capacidade e endereço")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Espaço criado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaceResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos (capacidade <= 0, nome vazio, endereço vazio)"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<PlaceResponseDTO> create(@RequestBody PlaceRequestDTO dtoRequest) {
    return super.create(dtoRequest);
  }

  @Override
  @Operation(summary = "Buscar espaço por ID", description = "Retorna os dados de um espaço específico")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Espaço encontrado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaceResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Espaço não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<PlaceResponseDTO> findById(@PathVariable Long id) {
    return super.findById(id);
  }

  @Override
  @Operation(summary = "Listar todos os espaços", description = "Retorna uma lista de todos os espaços cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de espaços retornada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaceResponseDTO.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<List<PlaceResponseDTO>> findAll() {
    return super.findAll();
  }

  @Override
  @Operation(summary = "Atualizar espaço", description = "Atualiza os dados de um espaço existente (nome, capacidade, endereço)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Espaço atualizado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaceResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos (capacidade <= 0)"),
      @ApiResponse(responseCode = "404", description = "Espaço não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<PlaceResponseDTO> update(@PathVariable Long id, @RequestBody PlaceRequestDTO dto) {
    return super.update(id, dto);
  }

  @Override
  @Operation(summary = "Deletar espaço", description = "Remove um espaço do sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Espaço deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Espaço não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return super.delete(id);
  }

  @Override
  protected PlaceResponseDTO toResponseDTO(Place place) {
    return new PlaceResponseDTO(
      place.getName(),
      place.getCapacity(),
      place.getAddress(),
      place.getId());
  }
}
