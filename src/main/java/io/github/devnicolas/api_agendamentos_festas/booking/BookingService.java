package io.github.devnicolas.api_agendamentos_festas.booking;

import io.github.devnicolas.api_agendamentos_festas.booking.dtos.BookingRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.BaseServiceImpl;
import io.github.devnicolas.api_agendamentos_festas.place.Place;
import io.github.devnicolas.api_agendamentos_festas.place.PlaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService extends BaseServiceImpl<Booking, BookingRequestDTO, Long> {

  private final PlaceRepository placeRepository;

  public BookingService(BookingRepository bookingRepository, PlaceRepository placeRepository) {
    super(bookingRepository);
    this.placeRepository = placeRepository;
  }

  @Override
  protected Booking toEntity(BookingRequestDTO dto) {

    Place place = placeRepository.findById(dto.placeId())
      .orElseThrow(() -> new RuntimeException("Espaço não encontrado"));


    return new Booking(
      place,
      dto.clientName(),
      dto.eventDate(),
      dto.value(),
      dto.partyPackage(),
      dto.dateEnd()
    );

  }

  @Override
  protected void updateEntity(Booking booking, BookingRequestDTO dto) {

    if (dto.placeId() != null) {
      Place place = placeRepository.findById(dto.placeId())
        .orElseThrow(() -> new RuntimeException("Espaço não encontrado"));
      booking.changePlace(place);
    }

    if (dto.clientName() != null) booking.changeClientName(dto.clientName());
    if (dto.value() != null) booking.changeValue(dto.value());
    if (dto.partyPackage() != null) booking.changePartyPackage(dto.partyPackage());

    // Reagendamento
    if (dto.eventDate() != null || dto.dateEnd() != null) {

      LocalDateTime newEventDate = dto.eventDate() != null
        ? dto.eventDate()
        : booking.getEventDate();

      LocalDateTime newDateEnd = dto.dateEnd() != null
        ? dto.dateEnd()
        : booking.getDateEnd();

      booking.reschedule(newEventDate, newDateEnd);
    }
  }

}
