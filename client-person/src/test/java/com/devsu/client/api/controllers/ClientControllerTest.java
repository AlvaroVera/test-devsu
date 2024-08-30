package com.devsu.client.api.controllers;

import com.devsu.client.api.dto.ClientDTO;
import com.devsu.client.api.exceptions.ResourceNotFoundException;
import com.devsu.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@Validated
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateClient() {
        // Arrange
        ClientDTO clientDTO = ClientDTO.builder()
                .clientId(1L)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build();
        when(clientService.createClient(any(ClientDTO.class))).thenReturn(clientDTO);

        // Act
        ResponseEntity<ClientDTO> response = clientController.createClient(clientDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
    }

    @Test
    public void testGetClientById_Success() {
        // Arrange
        Long id = 1L;
        ClientDTO clientDTO = ClientDTO.builder()
                .clientId(id)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build();
        when(clientService.getClientById(id)).thenReturn(Optional.of(clientDTO));

        // Act
        ResponseEntity<ClientDTO> response = clientController.getClientById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
    }

    @Test
    public void testGetClientById_NotFound() {
        // Arrange
        Long id = 1L;
        when(clientService.getClientById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientController.getClientById(id);
        });
        assertEquals("Cliente no encontrado con el id: " + id, thrown.getMessage());
    }

    @Test
    public void testGetAllClients() {
        // Arrange
        List<ClientDTO> clients = List.of(ClientDTO.builder()
                .clientId(1L)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build());
        when(clientService.getAllClients()).thenReturn(clients);

        // Act
        List<ClientDTO> response = clientController.getAllClients();

        // Assert
        assertEquals(clients, response);
    }

    @Test
    public void testUpdateClient_Success() {
        // Arrange
        Long id = 1L;
        ClientDTO clientDTO = ClientDTO.builder()
                .clientId(id)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build();
        when(clientService.updateClient(eq(id), any(ClientDTO.class))).thenReturn(Optional.of(clientDTO));

        // Act
        ResponseEntity<ClientDTO> response = clientController.updateClient(id, clientDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
    }

    @Test
    public void testUpdateClient_NotFound() {
        // Arrange
        Long id = 1L;
        ClientDTO clientDTO = ClientDTO.builder()
                .clientId(id)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build();
        when(clientService.updateClient(eq(id), any(ClientDTO.class))).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientController.updateClient(id, clientDTO);
        });
        assertEquals("Cliente no encontrado con el id: " + id, thrown.getMessage());
    }

    @Test
    public void testPartiallyUpdateClient_Success() {
        // Arrange
        Long id = 1L;
        ClientDTO clientDTO = ClientDTO.builder()
                .clientId(id)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build();
        when(clientService.partiallyUpdateClient(eq(id), anyMap())).thenReturn(Optional.of(clientDTO));

        // Act
        ResponseEntity<ClientDTO> response = clientController.partiallyUpdateClient(id, Map.of("nombre", "Juan"));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDTO, response.getBody());
    }

    @Test
    public void testPartiallyUpdateClient_NotFound() {
        // Arrange
        Long id = 1L;
        when(clientService.partiallyUpdateClient(eq(id), anyMap())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientController.partiallyUpdateClient(id, Map.of("nombre", "Juan"));
        });
        assertEquals("Cliente no encontrado con el id: " + id, thrown.getMessage());
    }

    @Test
    public void testDeleteClient_Success() {
        // Arrange
        Long id = 1L;
        when(clientService.deleteClient(id)).thenReturn(Optional.of(ClientDTO.builder()
                .clientId(id)
                .password("securePassword")
                .name("Juan")
                .gender('M')
                .birthdate(LocalDate.of(1990, 1, 1))
                .documentNumber("12345678")
                .address("Av. Siempre Viva 123")
                .phone("123456789")
                .build()));

        // Act
        ResponseEntity<Void> response = clientController.deleteClient(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteClient_NotFound() {
        // Arrange
        Long id = 1L;
        when(clientService.deleteClient(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientController.deleteClient(id);
        });
        assertEquals("Cliente no encontrado con el id: " + id, thrown.getMessage());
    }
}