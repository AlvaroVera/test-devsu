package com.devsu.client.service;

import com.devsu.client.api.dto.ClientDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    // GET - Retrieve a Client by ID
    Optional<ClientDTO> getClientById(Long clientId);

    // GET - Retrieve all Clients
    List<ClientDTO> getAllClients();

    // PUT - Update a Client (and Person in cascade)
    Optional<ClientDTO> updateClient(Long clientId, ClientDTO clientDTO);

    // PATCH - Partially update a Client (and Person in cascade)
    Optional<ClientDTO> partiallyUpdateClient(Long clientId, Map<String, Object> clientMap);

    // DELETE - Logically delete a Client (and Person in cascade)
    Optional<ClientDTO> deleteClient(Long clientId);

}
