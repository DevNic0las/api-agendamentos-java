package io.github.devnicolas.api_agendamentos_festas.booking;

import io.github.devnicolas.api_agendamentos_festas.booking.Enums.BookingStatus;
import io.github.devnicolas.api_agendamentos_festas.booking.Enums.PartyPackageEnum;
import io.github.devnicolas.api_agendamentos_festas.place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Domínio para Booking
 * Valida TODAS as regras de negócio sem Spring e sem Mockito
 */
@DisplayName("Booking Domain Tests")
class BookingDomainTest {

    private Place testPlace;
    private LocalDateTime futureEventDate;
    private LocalDateTime futureEndDate;

    @BeforeEach
    void setUp() {
        testPlace = new Place("Salão de Festas", 100, "Rua Principal, 123");
        futureEventDate = LocalDateTime.now().plusDays(10);
        futureEndDate = futureEventDate.plusHours(4);
    }

    // ============ CONSTRUCTOR TESTS ============

    @Test
    @DisplayName("Should create booking with valid data")
    void shouldCreateBookingWithValidData() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        assertNotNull(booking);
        assertEquals(testPlace, booking.getPlace());
        assertEquals("João Silva", booking.getClientName());
        assertEquals(futureEventDate, booking.getEventDate());
        assertEquals(new BigDecimal("500.00"), booking.getValue());
        assertEquals(PartyPackageEnum.BASIC, booking.getPartyPackage());
        assertEquals(futureEndDate, booking.getDateEnd());
        assertEquals(BookingStatus.PENDING, booking.getBookingStatus());
    }

    // ============ PLACE VALIDATION TESTS ============

    @Test
    @DisplayName("Should throw when place is null")
    void shouldThrowWhenPlaceIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Booking(
                null,
                "João Silva",
                futureEventDate,
                new BigDecimal("500.00"),
                PartyPackageEnum.BASIC,
                futureEndDate
            )
        );
        
        assertEquals("Place é obrigatório.", exception.getMessage());
    }

    // ============ CLIENT NAME VALIDATION TESTS ============

    @Test
    @DisplayName("Should accept null client name in constructor")
    void shouldAcceptNullClientNameInConstructor() {
        Booking booking = new Booking(
            testPlace,
            null,
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        assertNull(booking.getClientName());
    }

    @Test
    @DisplayName("Should throw when changing client name to null")
    void shouldThrowWhenChangingClientNameToNull() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changeClientName(null)
        );
        
        assertEquals("Nome do cliente é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw when changing client name to blank")
    void shouldThrowWhenChangingClientNameToBlank() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changeClientName("   ")
        );
        
        assertEquals("Nome do cliente é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Should update client name with valid value")
    void shouldUpdateClientNameWithValidValue() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.changeClientName("Maria Santos");
        
        assertEquals("Maria Santos", booking.getClientName());
    }

    // ============ DATE VALIDATION TESTS ============

    @Test
    @DisplayName("Should throw when event date is null")
    void shouldThrowWhenEventDateIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Booking(
                testPlace,
                "João Silva",
                null,
                new BigDecimal("500.00"),
                PartyPackageEnum.BASIC,
                futureEndDate
            )
        );
        
        assertEquals("Datas são obrigatórias.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw when end date is null")
    void shouldThrowWhenEndDateIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Booking(
                testPlace,
                "João Silva",
                futureEventDate,
                new BigDecimal("500.00"),
                PartyPackageEnum.BASIC,
                null
            )
        );
        
        assertEquals("Datas são obrigatórias.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw when end date is before event date")
    void shouldThrowWhenEndDateIsBeforeEventDate() {
        LocalDateTime invalidEndDate = futureEventDate.minusHours(1);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Booking(
                testPlace,
                "João Silva",
                futureEventDate,
                new BigDecimal("500.00"),
                PartyPackageEnum.BASIC,
                invalidEndDate
            )
        );
        
        assertEquals("A data final deve ser depois do início.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw when end date equals event date")
    void shouldThrowWhenEndDateEqualsEventDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Booking(
                testPlace,
                "João Silva",
                futureEventDate,
                new BigDecimal("500.00"),
                PartyPackageEnum.BASIC,
                futureEventDate
            )
        );
        
        assertEquals("A data final deve ser depois do início.", exception.getMessage());
    }

    @Test
    @DisplayName("Should accept when end date is after event date")
    void shouldAcceptWhenEndDateIsAfterEventDate() {
        LocalDateTime validEndDate = futureEventDate.plusSeconds(1);
        
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            validEndDate
        );
        
        assertEquals(futureEventDate, booking.getEventDate());
        assertEquals(validEndDate, booking.getDateEnd());
    }

    // ============ RESCHEDULE TESTS ============

    @Test
    @DisplayName("Should reschedule with valid dates")
    void shouldRescheduleWithValidDates() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        LocalDateTime newEventDate = futureEventDate.plusDays(5);
        LocalDateTime newEndDate = newEventDate.plusHours(4);
        
        booking.reschedule(newEventDate, newEndDate);
        
        assertEquals(newEventDate, booking.getEventDate());
        assertEquals(newEndDate, booking.getDateEnd());
    }

    @Test
    @DisplayName("Should throw when rescheduling with end date before event date")
    void shouldThrowWhenReschedulingWithInvalidDates() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        LocalDateTime newEventDate = futureEventDate.plusDays(5);
        LocalDateTime invalidNewEndDate = newEventDate.minusHours(1);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.reschedule(newEventDate, invalidNewEndDate)
        );
        
        assertEquals("A data final deve ser depois do início.", exception.getMessage());
    }

    // ============ VALUE VALIDATION TESTS ============

    @Test
    @DisplayName("Should accept value in constructor")
    void shouldAcceptValueInConstructor() {
        BigDecimal value = new BigDecimal("1500.50");
        
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            value,
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        assertEquals(value, booking.getValue());
    }

    @Test
    @DisplayName("Should throw when changing value to null")
    void shouldThrowWhenChangingValueToNull() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changeValue(null)
        );
        
        assertEquals("O valor deve ser maior que zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw when changing value to zero")
    void shouldThrowWhenChangingValueToZero() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changeValue(BigDecimal.ZERO)
        );
        
        assertEquals("O valor deve ser maior que zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw when changing value to negative")
    void shouldThrowWhenChangingValueToNegative() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changeValue(new BigDecimal("-100.00"))
        );
        
        assertEquals("O valor deve ser maior que zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Should update value with valid positive value")
    void shouldUpdateValueWithValidPositiveValue() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        BigDecimal newValue = new BigDecimal("1500.00");
        booking.changeValue(newValue);
        
        assertEquals(newValue, booking.getValue());
    }

    // ============ PARTY PACKAGE VALIDATION TESTS ============

    @Test
    @DisplayName("Should accept party package in constructor")
    void shouldAcceptPartyPackageInConstructor() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.PREMIUM,
            futureEndDate
        );
        
        assertEquals(PartyPackageEnum.PREMIUM, booking.getPartyPackage());
    }

    @Test
    @DisplayName("Should throw when changing party package to null")
    void shouldThrowWhenChangingPartyPackageToNull() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changePartyPackage(null)
        );
        
        assertEquals("Pacote é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Should update party package with valid value")
    void shouldUpdatePartyPackageWithValidValue() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.changePartyPackage(PartyPackageEnum.PREMIUM);
        
        assertEquals(PartyPackageEnum.PREMIUM, booking.getPartyPackage());
    }

    // ============ PLACE CHANGE TESTS ============

    @Test
    @DisplayName("Should throw when changing place to null")
    void shouldThrowWhenChangingPlaceToNull() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            booking.changePlace(null)
        );
        
        assertEquals("Place é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Should update place with valid value")
    void shouldUpdatePlaceWithValidValue() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        Place newPlace = new Place("Novo Salão", 200, "Rua Nova, 456");
        booking.changePlace(newPlace);
        
        assertEquals(newPlace, booking.getPlace());
    }

    // ============ STATUS TESTS ============

    @Test
    @DisplayName("Should create booking with PENDING status")
    void shouldCreateBookingWithPendingStatus() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        assertEquals(BookingStatus.PENDING, booking.getBookingStatus());
    }

    @Test
    @DisplayName("Should confirm pending booking")
    void shouldConfirmPendingBooking() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.confirm();
        
        assertEquals(BookingStatus.CONFIRMED, booking.getBookingStatus());
    }

    @Test
    @DisplayName("Should throw when confirming non-pending booking")
    void shouldThrowWhenConfirmingNonPendingBooking() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.confirm();
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            booking.confirm()
        );
        
        assertEquals("Só é possível confirmar uma reserva pendente.", exception.getMessage());
    }

    @Test
    @DisplayName("Should cancel pending booking")
    void shouldCancelPendingBooking() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.cancel();
        
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());
    }

    @Test
    @DisplayName("Should cancel confirmed booking")
    void shouldCancelConfirmedBooking() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        booking.confirm();
        
        booking.cancel();
        
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());
    }

    @Test
    @DisplayName("Should be idempotent when cancelling already cancelled booking")
    void shouldBeIdempotentWhenCancellingAlreadyCancelledBooking() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        booking.cancel();
        
        // Should not throw
        booking.cancel();
        
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());
    }

    // ============ EXPIRATION TESTS ============

    @Test
    @DisplayName("Should not expire when date is in future")
    void shouldNotExpireWhenDateIsInFuture() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.expireIfPastEndDate();
        
        assertEquals(BookingStatus.PENDING, booking.getBookingStatus());
    }

    @Test
    @DisplayName("Should not expire confirmed booking even if past end date")
    void shouldNotExpireConfirmedBookingEvenIfPastEndDate() {
        LocalDateTime pastEventDate = LocalDateTime.now().minusDays(5);
        LocalDateTime pastEndDate = pastEventDate.plusHours(4);
        
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            pastEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            pastEndDate
        );
        booking.confirm();
        
        booking.expireIfPastEndDate();
        
        assertEquals(BookingStatus.CONFIRMED, booking.getBookingStatus());
    }

    // ============ INTEGRATION TESTS ============

    @Test
    @DisplayName("Should maintain state consistency through multiple operations")
    void shouldMaintainStateConsistencyThroughMultipleOperations() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        // Make multiple updates
        booking.changeClientName("Maria");
        booking.changeValue(new BigDecimal("1500.00"));
        booking.changePartyPackage(PartyPackageEnum.PREMIUM);
        
        // Verify all changes persisted
        assertEquals("Maria", booking.getClientName());
        assertEquals(new BigDecimal("1500.00"), booking.getValue());
        assertEquals(PartyPackageEnum.PREMIUM, booking.getPartyPackage());
        assertEquals(BookingStatus.PENDING, booking.getBookingStatus());
    }

    @Test
    @DisplayName("Should allow reschedule after other changes")
    void shouldAllowRescheduleAfterOtherChanges() {
        Booking booking = new Booking(
            testPlace,
            "João Silva",
            futureEventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            futureEndDate
        );
        
        booking.changeValue(new BigDecimal("1000.00"));
        
        LocalDateTime newEventDate = futureEventDate.plusDays(5);
        LocalDateTime newEndDate = newEventDate.plusHours(4);
        
        booking.reschedule(newEventDate, newEndDate);
        
        assertEquals(newEventDate, booking.getEventDate());
        assertEquals(newEndDate, booking.getDateEnd());
        assertEquals(new BigDecimal("1000.00"), booking.getValue());
    }
}

