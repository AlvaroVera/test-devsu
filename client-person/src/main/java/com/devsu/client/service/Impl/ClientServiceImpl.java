package com.devsu.client.service.Impl;

import com.devsu.client.api.dto.ClientDTO;
import com.devsu.client.api.mapper.ClientMapper;
import com.devsu.client.domain.entities.Client;
import com.devsu.client.domain.entities.Person;
import com.devsu.client.domain.repositories.ClientRepository;
import com.devsu.client.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        return clientMapper.toDTO(clientRepository.save(client));
    }

    @Override
    public Optional<ClientDTO> getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .filter(Client::getActive)
                .map(clientMapper::toDTO);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .filter(Client::getActive)
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ClientDTO> updateClient(Long clientId, ClientDTO clientDTO) {
        return clientRepository.findById(clientId)
                .map(existingClient -> {
                    clientMapper.updateEntity(clientDTO, existingClient);
                    return clientRepository.save(existingClient);
                })
                .map(clientMapper::toDTO);
    }

    @Override
    @Transactional
    public Optional<ClientDTO> partiallyUpdateClient(Long clientId, Map<String, Object> clientMap) {
        return clientRepository.findById(clientId)
                .map(existingClient -> {

                    clientMapper.updateEntityPartial(clientMap, existingClient);

                    return clientRepository.save(existingClient);
                })
                .map(clientMapper::toDTO);
    }

    // DELETE - Logically delete a Client (and Person in cascade)
    @Override
    @Transactional
    public Optional<ClientDTO> deleteClient(Long clientId) {
        return clientRepository.findById(clientId)
                .map(existingClient -> {
                    existingClient.setActive(false);
                    existingClient.getPerson().setActive(false);
                    return clientMapper.toDTO(clientRepository.save(existingClient));
                });
    }
}
