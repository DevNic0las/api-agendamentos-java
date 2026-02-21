package io.github.devnicolas.api_agendamentos_festas.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de Domínio para Cliente
 * Valida TODAS as regras de negócio sem Spring e sem Mockito
 */
@DisplayName("Client Domain Tests")
class ClientDomainTest {

    // ============ CONSTRUCTOR TESTS ============

    @Test
    @DisplayName("Should create client with valid data")
    void shouldCreateClientWithValidData() {
        LocalDate dob = LocalDate.of(1990, 5, 15);

        Client client = new Client("João Silva", "(11) 98765-4321", dob);

        assertNotNull(client);
        assertEquals("João Silva", client.getName());
        assertEquals("(11) 98765-4321", client.getPhoneNumber());
        assertEquals(dob, client.getDateOfBirth());
    }

    // ============ NAME VALIDATION TESTS ============

    @Test
    @DisplayName("Should throw when name is null or blank")
    void shouldThrowWhenNameIsNullOrBlank() {
        LocalDate dob = LocalDate.of(1990, 5, 15);

        // Null case
        IllegalArgumentException exceptionNull = assertThrows(IllegalArgumentException.class, () ->
            new Client(null, "(11) 98765-4321", dob)
        );
        assertEquals("Nome não pode ser vazio", exceptionNull.getMessage());

        // Blank case
        IllegalArgumentException exceptionBlank = assertThrows(IllegalArgumentException.class, () ->
            new Client("   ", "(11) 98765-4321", dob)
        );
        assertEquals("Nome não pode ser vazio", exceptionBlank.getMessage());

        // Empty case
        assertThrows(IllegalArgumentException.class, () ->
            new Client("", "(11) 98765-4321", dob)
        );
    }

    @Test
    @DisplayName("Should allow name with special characters")
    void shouldAllowNameWithSpecialCharacters() {
        LocalDate dob = LocalDate.of(1990, 5, 15);
        String nameWithSpecialChars = "José da Silva-Nunes";

        Client client = new Client(nameWithSpecialChars, "(11) 98765-4321", dob);

        assertEquals(nameWithSpecialChars, client.getName());
    }

    // ============ PHONE VALIDATION TESTS ============

    @Test
    @DisplayName("Should throw when phone is null or blank")
    void shouldThrowWhenPhoneIsNullOrBlank() {
        LocalDate dob = LocalDate.of(1990, 5, 15);

        // Null case
        IllegalArgumentException exceptionNull = assertThrows(IllegalArgumentException.class, () ->
            new Client("João Silva", null, dob)
        );
        assertEquals("Telefone é obrigatório", exceptionNull.getMessage());

        // Blank case
        IllegalArgumentException exceptionBlank = assertThrows(IllegalArgumentException.class, () ->
            new Client("João Silva", "   ", dob)
        );
        assertEquals("Telefone é obrigatório", exceptionBlank.getMessage());

        // Empty case
        assertThrows(IllegalArgumentException.class, () ->
            new Client("João Silva", "", dob)
        );
    }

    @Test
    @DisplayName("Should accept different phone formats")
    void shouldAcceptDifferentPhoneFormats() {
        LocalDate dob = LocalDate.of(1990, 5, 15);
        String[] validPhones = {
            "(11) 98765-4321",
            "(21) 99999-8888",
            "+55 11 98765-4321",
            "11987654321"
        };

        for (String phone : validPhones) {
            Client client = new Client("João", phone, dob);
            assertEquals(phone, client.getPhoneNumber());
        }
    }

    // ============ DATE OF BIRTH VALIDATION TESTS ============

    @Test
    @DisplayName("Should throw when dateOfBirth is null")
    void shouldThrowWhenDateOfBirthIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new Client("João Silva", "(11) 98765-4321", null)
        );

        assertEquals("Data de nascimento é obrigatória", exception.getMessage());
    }

    @Test
    @DisplayName("Should accept valid birth date")
    void shouldAcceptValidBirthDate() {
        LocalDate dob = LocalDate.of(1990, 5, 15);

        Client client = new Client("João", "(11) 98765-4321", dob);

        assertEquals(dob, client.getDateOfBirth());
    }

    @Test
    @DisplayName("Should accept recent birth date")
    void shouldAcceptRecentBirthDate() {
        LocalDate recentDob = LocalDate.now().minusYears(18);

        Client client = new Client("João", "(11) 98765-4321", recentDob);

        assertEquals(recentDob, client.getDateOfBirth());
    }

    // ============ SETNAME TESTS ============

    @Test
    @DisplayName("Should update name with valid value")
    void shouldUpdateNameWithValidValue() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        client.setName("Maria");

        assertEquals("Maria", client.getName());
    }

    @Test
    @DisplayName("Should throw when updating name to null")
    void shouldThrowWhenUpdatingNameToNull() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        assertThrows(IllegalArgumentException.class, () -> client.setName(null));
    }

    @Test
    @DisplayName("Should throw when updating name to blank")
    void shouldThrowWhenUpdatingNameToBlank() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        assertThrows(IllegalArgumentException.class, () -> client.setName("   "));
    }

    // ============ SETPHONE TESTS ============

    @Test
    @DisplayName("Should update phone with valid value")
    void shouldUpdatePhoneWithValidValue() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        client.setPhoneNumber("(21) 99999-8888");

        assertEquals("(21) 99999-8888", client.getPhoneNumber());
    }

    @Test
    @DisplayName("Should throw when updating phone to null")
    void shouldThrowWhenUpdatingPhoneToNull() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        assertThrows(IllegalArgumentException.class, () -> client.setPhoneNumber(null));
    }

    @Test
    @DisplayName("Should throw when updating phone to blank")
    void shouldThrowWhenUpdatingPhoneToBlank() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        assertThrows(IllegalArgumentException.class, () -> client.setPhoneNumber("   "));
    }

    // ============ SETDATEOFBIRTH TESTS ============

    @Test
    @DisplayName("Should update dateOfBirth with valid value")
    void shouldUpdateDateOfBirthWithValidValue() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));
        LocalDate newDate = LocalDate.of(1985, 3, 20);

        client.setDateOfBirth(newDate);

        assertEquals(newDate, client.getDateOfBirth());
    }

    @Test
    @DisplayName("Should throw when updating dateOfBirth to null")
    void shouldThrowWhenUpdatingDateOfBirthToNull() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        assertThrows(IllegalArgumentException.class, () -> client.setDateOfBirth(null));
    }

    // ============ GETID TESTS ============

    @Test
    @DisplayName("Should return null for id before persistence")
    void shouldReturnNullForIdBeforePersistence() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        assertNull(client.getId());
    }

    // ============ INTEGRATION TESTS ============

    @Test
    @DisplayName("Should allow multiple updates in sequence")
    void shouldAllowMultipleUpdatesInSequence() {
        Client client = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        client.setName("Maria");
        client.setPhoneNumber("(21) 99999-8888");
        client.setDateOfBirth(LocalDate.of(1985, 3, 20));

        assertEquals("Maria", client.getName());
        assertEquals("(21) 99999-8888", client.getPhoneNumber());
        assertEquals(LocalDate.of(1985, 3, 20), client.getDateOfBirth());
    }

    @Test
    @DisplayName("Should maintain state consistency")
    void shouldMaintainStateConsistency() {
        LocalDate initialDob = LocalDate.of(1990, 5, 15);
        Client client = new Client("João", "(11) 98765-4321", initialDob);

        // Validate initial state
        assertEquals("João", client.getName());
        assertEquals("(11) 98765-4321", client.getPhoneNumber());
        assertEquals(initialDob, client.getDateOfBirth());

        // Change one field
        client.setName("Maria");

        // Other fields should remain unchanged
        assertEquals("(11) 98765-4321", client.getPhoneNumber());
        assertEquals(initialDob, client.getDateOfBirth());
    }
}

