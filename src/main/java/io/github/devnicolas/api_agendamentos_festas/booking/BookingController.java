package io.github.devnicolas.api_agendamentos_festas.booking;

import io.github.devnicolas.api_agendamentos_festas.booking.dtos.BookingRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.booking.dtos.BookingResponseDTO;
import io.github.devnicolas.api_agendamentos_festas.interfaces.services.controllers.BaseControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/booking")
public class BookingController extends BaseControllerImpl<Booking, BookingRequestDTO, BookingResponseDTO, Long> {

  public BookingController(BookingService bookingService) {
    super(bookingService);
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
