package io.github.devnicolas.api_agendamentos_festas.client.dtos;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(

        @NotBlank(message = "Nome não pode ser nulo")
        String name,
        @NotBlank(message = "Email não pode ser nulo")
        String email,
        @NotBlank(message = "Senha não pode ser nulo")
        String password
) {
}
