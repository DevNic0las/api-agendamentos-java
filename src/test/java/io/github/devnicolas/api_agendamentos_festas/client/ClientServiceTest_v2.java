package io.github.devnicolas.api_agendamentos_festas.client;

import io.github.devnicolas.api_agendamentos_festas.client.dtos.ClientRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes de Service para ClientService (com Mockito)
 * <p>
 * Valida APENAS:
 * - Interações com repositório
 * - Lançamento de exceções para dependências não encontradas
 * - Delegação correta para métodos da entidade
 * <p>
 * NÃO testa validações da entidade (já testadas em ClientDomainTest)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ClientService Tests")
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository, bCryptPasswordEncoder);
    }

    // ============ CREATE TESTS ============

    @Test
    @DisplayName("Should create client by delegating to repository")
    void shouldCreateClientByDelegatingToRepository() {
        // Arrange
        ClientRequestDTO dto = new ClientRequestDTO(
                "João Silva",
                "(11) 98765-4321",
                LocalDate.of(1990, 5, 15)
        );

        Client expectedClient = new Client(dto.name(), dto.numberOfPhone(), dto.dateOfBirth());
        when(clientRepository.save(any(Client.class))).thenReturn(expectedClient);

        // Act
        Client result = clientService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Should construct client correctly from DTO and delegate to repository")
    void shouldConstructClientCorrectlyFromDTOAndDelegateToRepository() {
        // Arrange
        LocalDate dob = LocalDate.of(1985, 3, 20);
        ClientRequestDTO dto = new ClientRequestDTO("Maria", "(21) 99999-8888", dob);

        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Client result = clientService.create(dto);

        // Assert
        assertEquals("Maria", result.getName());
        assertEquals("(21) 99999-8888", result.getPhoneNumber());
        assertEquals(dob, result.getDateOfBirth());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Should not save to repository when domain validation fails on create")
    void shouldNotSaveToRepositoryWhenDomainValidationFailsOnCreate() {
        // Arrange
        ClientRequestDTO invalidDto = new ClientRequestDTO(null, "(11) 98765-4321", LocalDate.of(1990, 5, 15));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientService.create(invalidDto));
        verify(clientRepository, never()).save(any(Client.class));
    }

    // ============ FIND TESTS ============

    @Test
    @DisplayName("Should find client by id")
    void shouldFindClientById() {
        // Arrange
        Long clientId = 1L;
        Client expectedClient = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        // Act
        Optional<Client> result = clientService.findById(clientId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("João", result.get().getName());
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    @DisplayName("Should return empty optional when client not found by id")
    void shouldReturnEmptyOptionalWhenClientNotFoundById() {
        // Arrange
        Long clientId = 999L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act
        Optional<Client> result = clientService.findById(clientId);

        // Assert
        assertFalse(result.isPresent());
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    @DisplayName("Should find all clients")
    void shouldFindAllClients() {
        // Arrange
        Client client1 = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));
        Client client2 = new Client("Maria", "(21) 99999-8888", LocalDate.of(1985, 3, 20));
        List<Client> clients = List.of(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        // Act
        List<Client> result = clientService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no clients exist")
    void shouldReturnEmptyListWhenNoClientsExist() {
        // Arrange
        when(clientRepository.findAll()).thenReturn(List.of());

        // Act
        List<Client> result = clientService.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(clientRepository, times(1)).findAll();
    }

    // ============ UPDATE TESTS ============

    @Test
    @DisplayName("Should update client when found")
    void shouldUpdateClientWhenFound() {
        // Arrange
        Long clientId = 1L;
        Client existingClient = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));
        ClientRequestDTO updateDto = new ClientRequestDTO("Maria", "(21) 99999-8888", LocalDate.of(1985, 3, 20));

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(existingClient);

        // Act
        Client result = clientService.update(clientId, updateDto);

        // Assert
        assertNotNull(result);
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when updating non-existent client")
    void shouldThrowRuntimeExceptionWhenUpdatingNonExistentClient() {
        // Arrange
        Long clientId = 999L;
        ClientRequestDTO updateDto = new ClientRequestDTO("Maria", "(21) 99999-8888", LocalDate.of(1985, 3, 20));

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clientService.update(clientId, updateDto));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("Should not save to repository when domain validation fails on update")
    void shouldNotSaveToRepositoryWhenDomainValidationFailsOnUpdate() {
        // Arrange
        Long clientId = 1L;
        Client existingClient = new Client("João", "(11) 98765-4321", LocalDate.of(1990, 5, 15));
        ClientRequestDTO invalidDto = new ClientRequestDTO(null, "(21) 99999-8888", LocalDate.of(1985, 3, 20));

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clientService.update(clientId, invalidDto));
        verify(clientRepository, never()).save(any(Client.class));
    }

    // ============ DELETE TESTS ============

    @Test
    @DisplayName("Should delete client when exists")
    void shouldDeleteClientWhenExists() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(true);

        // Act
        clientService.delete(clientId);

        // Assert
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleting non-existent client")
    void shouldThrowRuntimeExceptionWhenDeletingNonExistentClient() {
        // Arrange
        Long clientId = 999L;
        when(clientRepository.existsById(clientId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clientService.delete(clientId));
        verify(clientRepository, never()).deleteById(clientId);
    }
}

