package io.github.devnicolas.api_agendamentos_festas.booking;

import io.github.devnicolas.api_agendamentos_festas.booking.dtos.BookingRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.booking.dtos.BookingResponseDTO;
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

import java.util.List;


@RestController
@RequestMapping("/booking")
@Tag(name = "Bookings", description = "Gerenciamento de agendamentos de festas")
public class BookingController extends BaseControllerImpl<Booking, BookingRequestDTO, BookingResponseDTO, Long> {

  public BookingController(BookingService bookingService) {
    super(bookingService);
  }

  @Override
  @Operation(summary = "Criar novo agendamento", description = "Cria um novo agendamento de festa com espaço, cliente, datas, valor e pacote")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos (place não existe, datas inválidas, value <= 0)"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<BookingResponseDTO> create(@RequestBody BookingRequestDTO dtoRequest) {
    return super.create(dtoRequest);
  }

  @Override
  @Operation(summary = "Buscar agendamento por ID", description = "Retorna os dados de um agendamento específico")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Agendamento encontrado",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<BookingResponseDTO> findById(@PathVariable Long id) {
    return super.findById(id);
  }

  @Override
  @Operation(summary = "Listar todos os agendamentos", description = "Retorna uma lista de todos os agendamentos cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDTO.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<List<BookingResponseDTO>> findAll() {
    return super.findAll();
  }

  @Override
  @Operation(summary = "Atualizar agendamento", description = "Atualiza os dados de um agendamento existente (cliente, datas, valor, pacote, espaço)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos (datas inválidas, value <= 0, place não existe)"),
      @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<BookingResponseDTO> update(@PathVariable Long id, @RequestBody BookingRequestDTO dto) {
    return super.update(id, dto);
  }

  @Override
  @Operation(summary = "Deletar agendamento", description = "Remove um agendamento do sistema")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @SecurityRequirement(name = "bearer-jwt")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return super.delete(id);
  }

  @Override
  protected BookingResponseDTO toResponseDTO(Booking booking) {
    return new BookingResponseDTO(
      booking.getId(),
      booking.getPlace().getId(),
      booking.getClientName(),
      booking.getBookingStatus(),
      booking.getValue(),
      booking.getPartyPackage(),
      booking.getEventDate(),
      booking.getDateEnd(),
      booking.getCreatedAt()
    );
  }

}
