package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.entities.Client;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

        private ClientService clientService = mock(ClientService.class);
        private ClientController clientController = new ClientController(clientService);

        @Test
        void createClientTest() {
                // Arrange
                ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999",
                                true);
                ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address",
                                "9999999999",
                                true);
                when(clientService.create(newClient)).thenReturn(createdClient);

                // Act
                ResponseEntity<ClientDto> response = clientController.create(newClient);

                // Assert
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertEquals(createdClient, response.getBody());
        }

        @Test
        void clientDomainF5_setters_Test() {
                // Arrange
                Client client = Client.builder()
                                .id(1L)
                                .name("Name")
                                .dni("00000")
                                .gender("F")
                                .age(20)
                                .address("address name")
                                .phone("000-100")
                                .password("1234")
                                .isActive(true)
                                .build();

                // Act
                client.setName("NewName");
                client.setActive(false);

                // Assert
                assertEquals(client.getName(), "NewName");
                assertEquals(client.isActive(), false);
        }

        @Test
        void integrationTestF6_create_Test() throws Exception {
                // Arrange
                ClientDto createClientDto = ClientDto.builder()
                                .id(999L)
                                .name("Cristian Herrera")
                                .dni("1500000000")
                                .gender("M")
                                .age(21)
                                .address("Tena")
                                .phone("+593960279073")
                                .password("admin123")
                                .isActive(true)
                                .build();

                ClientDto createdClientDto = ClientDto.builder()
                                .id(1L)
                                .name("Hello")
                                .dni("170700000000")
                                .gender("F")
                                .age(100)
                                .address("Quito")
                                .phone("+593000000000")
                                .password("mypassword")
                                .isActive(false)
                                .build();

                when(clientService.create(createClientDto)).thenReturn(createdClientDto);

                // Act
                ResponseEntity<ClientDto> response = clientController.create(createClientDto);

                // Assert
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertEquals(createdClientDto, response.getBody());

                verify(clientService).create(createClientDto);
        }

}
