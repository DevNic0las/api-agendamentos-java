package io.github.devnicolas.api_agendamentos_festas.client.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientRequestDTO(

  @NotBlank(message = "Nome é obrigatório do dto")
  String name,

  @NotBlank(message = "Telefone é obrigatório")
  @Pattern(
    regexp = "^[0-9()+\\-\\s]{8,20}$",
    message = "Telefone inválido"
  )
  String numberOfPhone,

  @NotNull(message = "Data de nascimento deve estar no formato YYYY-MM-DD")
  LocalDate dateOfBirth

) {}
