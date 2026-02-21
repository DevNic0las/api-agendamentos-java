package io.github.devnicolas.api_agendamentos_festas.booking;

import io.github.devnicolas.api_agendamentos_festas.booking.dtos.BookingRequestDTO;
import io.github.devnicolas.api_agendamentos_festas.place.Place;
import io.github.devnicolas.api_agendamentos_festas.place.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import io.github.devnicolas.api_agendamentos_festas.booking.Enums.PartyPackageEnum;

/**
 * Testes de Service para BookingService (com Mockito)
 *
 * Valida APENAS:
 * - Interações com repositórios
 * - Lançamento de exceções quando Place não é encontrado
 * - Delegação correta para métodos da entidade
 *
 * NÃO testa validações da entidade (já testadas em BookingDomainTest)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BookingService Tests")
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PlaceRepository placeRepository;

    private BookingService bookingService;
    private Place testPlace;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository, placeRepository);
        testPlace = new Place("Salão de Festas", 100, "Rua Principal, 123");
    }

    // ============ CREATE TESTS ============

    @Test
    @DisplayName("Should create booking by finding place and delegating to repository")
    void shouldCreateBookingByFindingPlaceAndDelegatingToRepository() {
        // Arrange
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        LocalDateTime dateEnd = eventDate.plusHours(4);

        BookingRequestDTO dto = new BookingRequestDTO(
            1L,
            "João Silva",
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            eventDate,
            dateEnd
        );

        when(placeRepository.findById(1L)).thenReturn(Optional.of(testPlace));
        Booking expectedBooking = new Booking(
            testPlace,
            dto.clientName(),
            dto.eventDate(),
            dto.value(),
            dto.partyPackage(),
            dto.dateEnd()
        );
        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);

        // Act
        Booking result = bookingService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.getClientName());
        verify(placeRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when place not found on create")
    void shouldThrowRuntimeExceptionWhenPlaceNotFoundOnCreate() {
        // Arrange
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        BookingRequestDTO dto = new BookingRequestDTO(
            999L,
            "João Silva",
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            eventDate,
            eventDate.plusHours(4)
        );

        when(placeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.create(dto));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should not save to repository when domain validation fails on create")
    void shouldNotSaveToRepositoryWhenDomainValidationFailsOnCreate() {
        // Arrange
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        LocalDateTime invalidDateEnd = eventDate.minusHours(1);

        BookingRequestDTO dto = new BookingRequestDTO(
            1L,
            "João Silva",
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            eventDate,
            invalidDateEnd
        );

        when(placeRepository.findById(1L)).thenReturn(Optional.of(testPlace));

        // Act & Assert - Verifica TANTO que exception é lançada QUANTO que não salva
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> bookingService.create(dto));

        assertEquals("A data final deve ser depois do início.", exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    // ============ FIND TESTS ============

    @Test
    @DisplayName("Should find booking by id")
    void shouldFindBookingById() {
        // Arrange
        Long bookingId = 1L;
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        Booking expectedBooking = new Booking(
            testPlace,
            "João Silva",
            eventDate,
            new BigDecimal("500.00"),
            PartyPackageEnum.BASIC,
            eventDate.plusHours(4)
        );
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(expectedBooking));

        // Act
        Optional<Booking> result = bookingService.findById(bookingId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getClientName());
        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    @DisplayName("Should return empty optional when booking not found by id")
    void shouldReturnEmptyOptionalWhenBookingNotFoundById() {
        // Arrange
        Long bookingId = 999L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act
        Optional<Booking> result = bookingService.findById(bookingId);

        // Assert
        assertFalse(result.isPresent());
        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    @DisplayName("Should find all bookings")
    void shouldFindAllBookings() {
        // Arrange
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        Booking booking1 = new Booking(testPlace, "João", eventDate, new BigDecimal("500.00"), PartyPackageEnum.BASIC, eventDate.plusHours(4));
        Booking booking2 = new Booking(testPlace, "Maria", eventDate, new BigDecimal("750.00"), PartyPackageEnum.PREMIUM, eventDate.plusHours(4));
        List<Booking> bookings = List.of(booking1, booking2);

        when(bookingRepository.findAll()).thenReturn(bookings);

        // Act
        List<Booking> result = bookingService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    // ============ UPDATE TESTS ============

    @Test
    @DisplayName("Should update booking when found")
    void shouldUpdateBookingWhenFound() {
        // Arrange
        Long bookingId = 1L;
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        Booking existingBooking = new Booking(testPlace, "João", eventDate, new BigDecimal("500.00"), PartyPackageEnum.BASIC, eventDate.plusHours(4));
        BookingRequestDTO updateDto = new BookingRequestDTO(
            1L,
            "Maria",
            new BigDecimal("750.00"),
            PartyPackageEnum.PREMIUM,
            eventDate,
            eventDate.plusHours(4)
        );

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(placeRepository.findById(1L)).thenReturn(Optional.of(testPlace));
        when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

        // Act
        Booking result = bookingService.update(bookingId, updateDto);

        // Assert
        assertNotNull(result);
        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when updating non-existent booking")
    void shouldThrowRuntimeExceptionWhenUpdatingNonExistentBooking() {
        // Arrange
        Long bookingId = 999L;
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        BookingRequestDTO updateDto = new BookingRequestDTO(
            1L,
            "Maria",
            new BigDecimal("750.00"),
            PartyPackageEnum.PREMIUM,
            eventDate,
            eventDate.plusHours(4)
        );

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.update(bookingId, updateDto));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when updating booking with non-existent place")
    void shouldThrowRuntimeExceptionWhenUpdatingBookingWithNonExistentPlace() {
        // Arrange
        Long bookingId = 1L;
        LocalDateTime eventDate = LocalDateTime.now().plusDays(10);
        Booking existingBooking = new Booking(testPlace, "João", eventDate, new BigDecimal("500.00"), PartyPackageEnum.BASIC, eventDate.plusHours(4));
        BookingRequestDTO updateDto = new BookingRequestDTO(
            999L,
            "Maria",
            new BigDecimal("750.00"),
            PartyPackageEnum.PREMIUM,
            eventDate,
            eventDate.plusHours(4)
        );

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(existingBooking));
        when(placeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.update(bookingId, updateDto));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    // ============ DELETE TESTS ============

    @Test
    @DisplayName("Should delete booking when exists")
    void shouldDeleteBookingWhenExists() {
        // Arrange
        Long bookingId = 1L;
        when(bookingRepository.existsById(bookingId)).thenReturn(true);

        // Act
        bookingService.delete(bookingId);

        // Assert
        verify(bookingRepository, times(1)).existsById(bookingId);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleting non-existent booking")
    void shouldThrowRuntimeExceptionWhenDeletingNonExistentBooking() {
        // Arrange
        Long bookingId = 999L;
        when(bookingRepository.existsById(bookingId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> bookingService.delete(bookingId));
        verify(bookingRepository, never()).deleteById(bookingId);
    }
}

