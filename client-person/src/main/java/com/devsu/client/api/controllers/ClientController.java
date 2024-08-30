package com.devsu.client.api.controllers;


import com.devsu.client.api.dto.ClientDTO;
import com.devsu.client.api.exceptions.ResourceNotFoundException;
import com.devsu.client.service.ClientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return  new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id)
                .map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el id: " + id));
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("id") Long id, @Valid @RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(id, clientDTO)
                .map(updatedClient -> new ResponseEntity<>(updatedClient, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el id: " + id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> partiallyUpdateClient(@PathVariable("id") Long id, @RequestBody Map<String, Object> clientMap) {
        return clientService.partiallyUpdateClient(id, clientMap)
                .map(updatedClient -> new ResponseEntity<>(updatedClient, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        return clientService.deleteClient(id)
                .map(clientDTO -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el id: " + id));
    }
}
