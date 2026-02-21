package io.github.devnicolas.api_agendamentos_festas.place;

import io.github.devnicolas.api_agendamentos_festas.place.dtos.PlaceRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes de Service para PlaceService (com Mockito)
 *
 * Valida APENAS:
 * - Interações com repositório
 * - Lançamento de exceções para dependências não encontradas
 * - Delegação correta para métodos da entidade
 *
 * NÃO testa validações da entidade (já testadas em PlaceDomainTest)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PlaceService Tests")
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        placeService = new PlaceService(placeRepository);
    }

    // ============ CREATE TESTS ============

    @Test
    @DisplayName("Should create place by delegating to repository")
    void shouldCreatePlaceByDelegatingToRepository() {
        // Arrange
        PlaceRequestDTO dto = new PlaceRequestDTO("Salão de Festas", 100, "Rua Principal, 123");

        Place expectedPlace = new Place(dto.name(), dto.capacity(), dto.address());
        when(placeRepository.save(any(Place.class))).thenReturn(expectedPlace);

        // Act
        Place result = placeService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Salão de Festas", result.getName());
        assertEquals(100, result.getCapacity());
        assertEquals("Rua Principal, 123", result.getAddress());
        verify(placeRepository, times(1)).save(any(Place.class));
    }

    @Test
    @DisplayName("Should not save to repository when domain validation fails on create")
    void shouldNotSaveToRepositoryWhenDomainValidationFailsOnCreate() {
        // Arrange
        PlaceRequestDTO invalidDto = new PlaceRequestDTO("Salão", 0, "Rua 123");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> placeService.create(invalidDto));
        verify(placeRepository, never()).save(any(Place.class));
    }

    // ============ FIND TESTS ============

    @Test
    @DisplayName("Should find place by id")
    void shouldFindPlaceById() {
        // Arrange
        Long placeId = 1L;
        Place expectedPlace = new Place("Salão", 100, "Rua 123");
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(expectedPlace));

        // Act
        Optional<Place> result = placeService.findById(placeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Salão", result.get().getName());
        assertEquals(100, result.get().getCapacity());
        verify(placeRepository, times(1)).findById(placeId);
    }

    @Test
    @DisplayName("Should return empty optional when place not found by id")
    void shouldReturnEmptyOptionalWhenPlaceNotFoundById() {
        // Arrange
        Long placeId = 999L;
        when(placeRepository.findById(placeId)).thenReturn(Optional.empty());

        // Act
        Optional<Place> result = placeService.findById(placeId);

        // Assert
        assertFalse(result.isPresent());
        verify(placeRepository, times(1)).findById(placeId);
    }

    @Test
    @DisplayName("Should find all places")
    void shouldFindAllPlaces() {
        // Arrange
        Place place1 = new Place("Salão 1", 100, "Rua 1");
        Place place2 = new Place("Salão 2", 200, "Rua 2");
        List<Place> places = List.of(place1, place2);

        when(placeRepository.findAll()).thenReturn(places);

        // Act
        List<Place> result = placeService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(placeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no places exist")
    void shouldReturnEmptyListWhenNoPlacesExist() {
        // Arrange
        when(placeRepository.findAll()).thenReturn(List.of());

        // Act
        List<Place> result = placeService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(placeRepository, times(1)).findAll();
    }

    // ============ UPDATE TESTS ============

    @Test
    @DisplayName("Should update place when found")
    void shouldUpdatePlaceWhenFound() {
        // Arrange
        Long placeId = 1L;
        Place existingPlace = new Place("Salão Original", 100, "Rua Original");
        PlaceRequestDTO updateDto = new PlaceRequestDTO("Novo Salão", 200, "Nova Rua");

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(existingPlace));
        when(placeRepository.save(any(Place.class))).thenReturn(existingPlace);

        // Act
        Place result = placeService.update(placeId, updateDto);

        // Assert
        assertNotNull(result);
        verify(placeRepository, times(1)).findById(placeId);
        verify(placeRepository, times(1)).save(any(Place.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when updating non-existent place")
    void shouldThrowRuntimeExceptionWhenUpdatingNonExistentPlace() {
        // Arrange
        Long placeId = 999L;
        PlaceRequestDTO updateDto = new PlaceRequestDTO("Novo Salão", 200, "Nova Rua");

        when(placeRepository.findById(placeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> placeService.update(placeId, updateDto));
        verify(placeRepository, never()).save(any(Place.class));
    }

    @Test
    @DisplayName("Should not save to repository when domain validation fails on update")
    void shouldNotSaveToRepositoryWhenDomainValidationFailsOnUpdate() {
        // Arrange
        Long placeId = 1L;
        Place existingPlace = new Place("Salão", 100, "Rua 123");
        PlaceRequestDTO invalidDto = new PlaceRequestDTO("Novo", 0, "Nova Rua");

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(existingPlace));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> placeService.update(placeId, invalidDto));
        verify(placeRepository, never()).save(any(Place.class));
    }

    // ============ DELETE TESTS ============

    @Test
    @DisplayName("Should delete place when exists")
    void shouldDeletePlaceWhenExists() {
        // Arrange
        Long placeId = 1L;
        when(placeRepository.existsById(placeId)).thenReturn(true);

        // Act
        placeService.delete(placeId);

        // Assert
        verify(placeRepository, times(1)).existsById(placeId);
        verify(placeRepository, times(1)).deleteById(placeId);
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleting non-existent place")
    void shouldThrowRuntimeExceptionWhenDeletingNonExistentPlace() {
        // Arrange
        Long placeId = 999L;
        when(placeRepository.existsById(placeId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> placeService.delete(placeId));
        verify(placeRepository, never()).deleteById(placeId);
    }
}

