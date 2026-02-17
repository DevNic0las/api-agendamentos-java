package io.github.devnicolas.api_agendamentos_festas.booking.dtos;

import io.github.devnicolas.api_agendamentos_festas.booking.Enums.BookingStatus;
import io.github.devnicolas.api_agendamentos_festas.booking.Enums.PartyPackageEnum;
import io.github.devnicolas.api_agendamentos_festas.place.Place;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponseDTO(

  Long id,
  Long placeId,
  String clientName,
  BookingStatus bookingStatus,
  BigDecimal value,
  PartyPackageEnum partyPackage,
  LocalDateTime eventDate,
  LocalDateTime dateEnd,
  LocalDateTime createdAt

) {
}
