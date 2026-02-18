package io.github.devnicolas.api_agendamentos_festas.booking;

import io.github.devnicolas.api_agendamentos_festas.booking.Enums.BookingStatus;
import io.github.devnicolas.api_agendamentos_festas.booking.Enums.PartyPackageEnum;
import io.github.devnicolas.api_agendamentos_festas.place.Place;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id", nullable = false)
  private Place place;

  @Column(name = "client_name", nullable = false)
  private String clientName;

  @Enumerated(EnumType.STRING)
  @Column(name = "booking_status", nullable = false, length = 50)
  private BookingStatus bookingStatus;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal value;

  @Enumerated(EnumType.STRING)
  @Column(name = "party_package", nullable = false, length = 50)
  private PartyPackageEnum partyPackage;

  @Column(name = "event_date", nullable = false)
  private LocalDateTime eventDate;

  @Column(name = "date_end", nullable = false)
  private LocalDateTime dateEnd;


  @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
  private LocalDateTime createdAt;

  protected Booking() {

  }

  public Booking(
    Place place,
    String clientName,
    LocalDateTime eventDate,
    BigDecimal value,
    PartyPackageEnum partyPackage,
    LocalDateTime dateEnd
  ) {
    this.place = place;
    this.clientName = clientName;
    this.eventDate = eventDate;
    this.value = value;
    this.partyPackage = partyPackage;
    this.dateEnd = dateEnd;
    this.bookingStatus = BookingStatus.PENDING;

    validateBooking();
  }

  private void validateBooking() {
    if (place == null) throw new IllegalArgumentException("Place é obrigatório.");
    if (eventDate == null || dateEnd == null)
      throw new IllegalArgumentException("Datas são obrigatórias.");
    if (!dateEnd.isAfter(eventDate))
      throw new IllegalArgumentException("A data final deve ser depois do início.");
  }

  public void changePlace(Place place) {
    this.place = place;
    validateBooking();
  }

  public void changeClientName(String clientName) {
    if (clientName == null || clientName.isBlank())
      throw new IllegalArgumentException("Nome do cliente é obrigatório.");
    this.clientName = clientName;
  }

  public void reschedule(LocalDateTime eventDate, LocalDateTime dateEnd) {
    this.eventDate = eventDate;
    this.dateEnd = dateEnd;
    validateBooking();
  }

  public void changeValue(BigDecimal value) {
    if (value == null || value.compareTo(BigDecimal.ZERO) <= 0)
      throw new IllegalArgumentException("O valor deve ser maior que zero.");
    this.value = value;
  }

  public void changePartyPackage(PartyPackageEnum partyPackage) {
    if (partyPackage == null)
      throw new IllegalArgumentException("Pacote é obrigatório.");
    this.partyPackage = partyPackage;
  }

  public void confirm() {
    if (this.bookingStatus != BookingStatus.PENDING)
      throw new IllegalStateException("Só é possível confirmar uma reserva pendente.");
    this.bookingStatus = BookingStatus.CONFIRMED;
  }

  public void cancel() {
    if (this.bookingStatus == BookingStatus.CANCELLED)
      return;
    this.bookingStatus = BookingStatus.CANCELLED;
  }

  public void expireIfPastEndDate() {
    if (bookingStatus == BookingStatus.PENDING && LocalDateTime.now().isAfter(dateEnd)) {
      bookingStatus = BookingStatus.EXPIRED;
    }
  }

  public Long getId() {
    return id;
  }

  public Place getPlace() {
    return place;
  }

  public String getClientName() {
    return clientName;
  }

  public BookingStatus getBookingStatus() {
    return bookingStatus;
  }

  public BigDecimal getValue() {
    return value;
  }

  public PartyPackageEnum getPartyPackage() {
    return partyPackage;
  }

  public LocalDateTime getEventDate() {
    return eventDate;
  }

  public LocalDateTime getDateEnd() {
    return dateEnd;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setPlace(Place place) {
    this.place = place;
  }

}
