package com.devsu.hackerearth.backend.client.model.mapper;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.entities.Client;

public class ClientMapper {
    
    public static ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .dni(client.getDni())
                .name(client.getName())
                .password(client.getPassword())
                .gender(client.getGender())
                .age(client.getAge())
                .address(client.getAddress())
                .phone(client.getPhone())
                .isActive(client.isActive())
                .build();
    }

    public static Client toEntity(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .dni(clientDto.getDni())
                .name(clientDto.getName())
                .password(clientDto.getPassword())
                .gender(clientDto.getGender())
                .age(clientDto.getAge())
                .address(clientDto.getAddress())
                .phone(clientDto.getPhone())
                .isActive(clientDto.isActive())
                .build();
    }    

}
