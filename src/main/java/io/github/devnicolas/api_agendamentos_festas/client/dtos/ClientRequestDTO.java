package io.github.devnicolas.api_agendamentos_festas.client.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ClientRequestDTO(

  @NotBlank(message = "Nome é obrigatório")
  String name,

  @NotBlank(message = "Telefone é obrigatório")
  @Pattern(
    regexp = "^[0-9()+\\-\\s]{8,20}$",
    message = "Telefone inválido"
  )
  String numberOfPhone,

  @NotBlank(message = "Data de nascimento é obrigatória")
  LocalDate dateOfBirth

) {}
