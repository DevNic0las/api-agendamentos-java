package io.github.devnicolas.api_agendamentos_festas.client.dtos;

import java.time.LocalDate;

public record ClientResponseDTO(
  String name, String numberOfPhone, LocalDate dateOfBirth, Long id
) {
}
