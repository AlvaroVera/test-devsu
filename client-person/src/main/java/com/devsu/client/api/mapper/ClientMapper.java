package com.devsu.client.api.mapper;

import com.devsu.client.api.dto.ClientDTO;
import com.devsu.client.domain.entities.Client;
import com.devsu.client.domain.entities.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ClientMapper {
    public Client toEntity(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }

        Client client = new Client();
        client.setPassword(clientDTO.getPassword());

        Person person = new Person();
        person.setName(clientDTO.getName());
        person.setGender(clientDTO.getGender());
        person.setBirthdate(clientDTO.getBirthdate());
        person.setDocumentNumber(clientDTO.getDocumentNumber());
        person.setAddress(clientDTO.getAddress());
        person.setPhone(clientDTO.getPhone());

        client.setPerson(person);

        return client;
    }


    public ClientDTO toDTO(Client client){
        return ClientDTO.builder()
                .clientId(client.getClientId())
                .address(client.getPerson().getAddress())
                .name(client.getPerson().getName())
                .gender(client.getPerson().getGender())
                .birthdate(client.getPerson().getBirthdate())
                .documentNumber(client.getPerson().getDocumentNumber())
                .phone(client.getPerson().getPhone())
                .build();
    }


    public void updateEntity(ClientDTO clientDTO, Client client) {
        if (clientDTO == null || client == null) {
            return;
        }

        client.setPassword(clientDTO.getPassword());

        Person person = client.getPerson();
        person.setName(clientDTO.getName());
        person.setGender(clientDTO.getGender());
        person.setBirthdate(clientDTO.getBirthdate());
        person.setDocumentNumber(clientDTO.getDocumentNumber());
        person.setAddress(clientDTO.getAddress());
        person.setPhone(clientDTO.getPhone());

        client.setPerson(person);
    }

    public void updateEntityPartial(Map<String, Object> clientMap, Client client) {
        if (clientMap.get("password") != null) {
            client.setPassword((String) clientMap.get("password"));
        }

        Person person = client.getPerson();

        if (clientMap.get("name") != null) {
            person.setName((String) clientMap.get("name"));
        }
        if (clientMap.get("gender") != null) {
            person.setGender((Character) clientMap.get("gender"));
        }
        if (clientMap.get("birthdate") != null) {
            person.setBirthdate((LocalDate) clientMap.get("birthdate"));
        }
        if (clientMap.get("documentNumber") != null) {
            person.setDocumentNumber((String) clientMap.get("documentNumber"));
        }
        if (clientMap.get("address") != null) {
            person.setAddress((String) clientMap.get("address"));
        }
        if (clientMap.get("phone") != null) {
            person.setPhone((String) clientMap.get("phone"));
        }

        client.setPerson(person);
    }
}
