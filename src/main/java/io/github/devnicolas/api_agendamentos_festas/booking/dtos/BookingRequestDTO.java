package io.github.devnicolas.api_agendamentos_festas.booking.dtos;

import io.github.devnicolas.api_agendamentos_festas.booking.Enums.PartyPackageEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingRequestDTO(
        @NotNull Long placeId,
        @NotBlank String clientName,
        @Positive BigDecimal value,
        @NotNull PartyPackageEnum partyPackage,
        @Future LocalDateTime eventDate,
        @Future LocalDateTime dateEnd
) {
}
