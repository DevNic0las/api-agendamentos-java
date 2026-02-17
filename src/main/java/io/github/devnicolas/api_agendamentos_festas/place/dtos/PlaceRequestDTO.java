package io.github.devnicolas.api_agendamentos_festas.place.dtos;

import jakarta.validation.constraints.NotBlank;


public record PlaceRequestDTO(
        @NotBlank(message = "Nome não pode ser nulo")
        String name,
        @NotBlank(message = "Capacidade não pode ser nulo")
        int capacity,
        @NotBlank(message = "Endereço não pode ser nulo")
        String address
) {
}
