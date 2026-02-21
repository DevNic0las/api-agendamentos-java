package io.github.devnicolas.api_agendamentos_festas.place;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Domínio para Place
 * Valida TODAS as regras de negócio sem Spring e sem Mockito
 */
@DisplayName("Place Domain Tests")
class PlaceDomainTest {

    // ============ CONSTRUCTOR TESTS ============

    @Test
    @DisplayName("Should create place with valid data")
    void shouldCreatePlaceWithValidData() {
        Place place = new Place("Salão de Festas", 100, "Rua Principal, 123");

        assertNotNull(place);
        assertEquals("Salão de Festas", place.getName());
        assertEquals(100, place.getCapacity());
        assertEquals("Rua Principal, 123", place.getAddress());
    }

    @Test
    @DisplayName("Should throw when capacity is zero")
    void shouldThrowWhenCapacityIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Place("Salão", 0, "Rua 123")
        );
    }

    @Test
    @DisplayName("Should throw when capacity is negative")
    void shouldThrowWhenCapacityIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Place("Salão", -50, "Rua 123")
        );
    }

    @Test
    @DisplayName("Should accept capacity of 1")
    void shouldAcceptCapacityOfOne() {
        Place place = new Place("Pequeno Espaço", 1, "Rua 123");

        assertEquals(1, place.getCapacity());
    }

    @Test
    @DisplayName("Should accept large capacity")
    void shouldAcceptLargeCapacity() {
        Place place = new Place("Grande Espaço", 10000, "Rua 123");

        assertEquals(10000, place.getCapacity());
    }

    // ============ NAME SETTER TESTS ============

    @Test
    @DisplayName("Should update name with valid value")
    void shouldUpdateNameWithValidValue() {
        Place place = new Place("Salão", 100, "Rua 123");

        place.setName("Novo Salão");

        assertEquals("Novo Salão", place.getName());
    }

    @Test
    @DisplayName("Should throw when setting name to null")
    void shouldThrowWhenSettingNameToNull() {
        Place place = new Place("Salão", 100, "Rua 123");
        assertThrows(IllegalArgumentException.class, () -> place.setName(null));
    }

    @Test
    @DisplayName("Should throw when setting name to empty")
    void shouldThrowWhenSettingNameToEmpty() {
        Place place = new Place("Salão", 100, "Rua 123");
        assertThrows(IllegalArgumentException.class, () -> place.setName(""));
    }

    // ============ CAPACITY SETTER TESTS ============

    @Test
    @DisplayName("Should update capacity with valid value")
    void shouldUpdateCapacityWithValidValue() {
        Place place = new Place("Salão", 100, "Rua 123");

        place.setCapacity(200);

        assertEquals(200, place.getCapacity());
    }

    @Test
    @DisplayName("Should throw when setting capacity to zero")
    void shouldThrowWhenSettingCapacityToZero() {
        Place place = new Place("Salão", 100, "Rua 123");

        assertThrows(IllegalArgumentException.class,
                () -> place.setCapacity(0));
    }

    @Test
    @DisplayName("Should throw when setting capacity to negative")
    void shouldThrowWhenSettingCapacityToNegative() {
        Place place = new Place("Salão", 100, "Rua 123");

        assertThrows(IllegalArgumentException.class, () -> place.setCapacity(-50));
    }

    // ============ ADDRESS SETTER TESTS ============

    @Test
    @DisplayName("Should update address with valid value")
    void shouldUpdateAddressWithValidValue() {
        Place place = new Place("Salão", 100, "Rua Principal");

        place.setAddress("Av. Paulista, 1000");

        assertEquals("Av. Paulista, 1000", place.getAddress());
    }

    @Test
    @DisplayName("Should throw when setting address to empty in constructor")
    void shouldThrowWhenAddressIsEmptyInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Place("Salão", 100, ""));
    }


    // ============ INTEGRATION TESTS ============

    @Test
    @DisplayName("Should maintain state consistency")
    void shouldMaintainStateConsistency() {
        Place place = new Place("Salão Original", 100, "Rua Original");

        place.setName("Novo Salão");
        place.setCapacity(200);
        place.setAddress("Nova Rua");

        assertEquals("Novo Salão", place.getName());
        assertEquals(200, place.getCapacity());
        assertEquals("Nova Rua", place.getAddress());
    }

    @Test
    @DisplayName("Should allow multiple updates in sequence")
    void shouldAllowMultipleUpdatesInSequence() {
        Place place = new Place("Salão", 100, "Rua 123");

        place.setName("Salão Premium");
        place.setCapacity(500);
        place.setAddress("Av. Principal, 1000");
        place.setName("Salão Deluxe");
        place.setCapacity(600);

        assertEquals("Salão Deluxe", place.getName());
        assertEquals(600, place.getCapacity());
        assertEquals("Av. Principal, 1000", place.getAddress());
    }

    @Test
    @DisplayName("Should allow updating only one field")
    void shouldAllowUpdatingOnlyOneField() {
        Place place = new Place("Salão", 100, "Rua 123");
        String originalName = place.getName();
        String originalAddress = place.getAddress();

        place.setCapacity(200);

        assertEquals(originalName, place.getName());
        assertEquals(200, place.getCapacity());
        assertEquals(originalAddress, place.getAddress());
    }

    @Test
    @DisplayName("Should create place with various valid capacities")
    void shouldCreatePlaceWithVariousValidCapacities() {
        int[] validCapacities = {1, 10, 50, 100, 500, 1000, 10000};

        for (int capacity : validCapacities) {
            Place place = new Place("Salão", capacity, "Rua 123");
            assertEquals(capacity, place.getCapacity());
        }
    }

    @Test
    @DisplayName("Should reject various invalid capacities in constructor")
    void shouldRejectVariousInvalidCapacitiesInConstructor() {
        int[] invalidCapacities = {0, -1, -10, -100, -1000};

        for (int capacity : invalidCapacities) {
            assertThrows(RuntimeException.class, () ->
                    new Place("Salão", capacity, "Rua 123")
            );
        }
    }
}

