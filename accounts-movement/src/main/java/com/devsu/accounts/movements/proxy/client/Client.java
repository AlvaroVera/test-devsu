package com.devsu.accounts.movements.proxy.client;


import com.devsu.accounts.movements.proxy.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-person", url = "http://client-person-ms:8098/api/clients")
public interface Client {
    @GetMapping("/{id}")
    ClientDTO getClientById(@PathVariable("id") Long id);
}
