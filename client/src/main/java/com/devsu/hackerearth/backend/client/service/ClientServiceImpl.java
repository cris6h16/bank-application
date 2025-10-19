package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.client.error.application.ApplicationErrors;
import com.devsu.hackerearth.backend.client.error.application.ApplicationException;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.model.entities.Client;
import com.devsu.hackerearth.backend.client.model.enums.AggregateTypeEnum;
import com.devsu.hackerearth.backend.client.model.enums.EventTypeEnum;
import com.devsu.hackerearth.backend.client.model.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.service.validator.ClientValidator;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final OutboxService outboxService;

	public ClientServiceImpl(ClientRepository clientRepository, OutboxService outboxService) {
		this.clientRepository = clientRepository;
		this.outboxService = outboxService;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll().stream()
				.map(ClientMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		Client account = clientRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ApplicationErrors.CLIENT_NOT_FOUND));

		return ClientMapper.toDto(account);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		ClientValidator.validateCreation(clientDto);

		checkClientExistence(clientDto);
		clientDto.setId(null);

		Client saved = clientRepository.save(ClientMapper.toEntity(clientDto));
		return ClientMapper.toDto(saved);
	}

	private void checkClientExistence(ClientDto dto) {
		if (clientRepository.existsByDni(dto.getDni())) {
			throw new ApplicationException(ApplicationErrors.CLIENT_DNI_ALREADY_EXISTS);
		}
	}

	private void checkClientExistence(ClientDto updateDto, Client existentClient) {
		if (!Objects.equals(existentClient.getDni(), updateDto.getDni())
				&& clientRepository.existsByDni(updateDto.getDni())) {
			throw new ApplicationException(ApplicationErrors.CLIENT_DNI_ALREADY_EXISTS);
		}
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		ClientValidator.validateUpdate(clientDto);

		Client client = clientRepository.findById(clientDto.getId())
				.orElseThrow(() -> new ApplicationException(ApplicationErrors.CLIENT_NOT_FOUND));

		checkClientExistence(clientDto, client);

		client.setName(clientDto.getName());
		client.setDni(clientDto.getDni());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setPassword(clientDto.getPassword());
		client.setActive(clientDto.isActive());

		return ClientMapper.toDto(clientRepository.save(client));

	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ApplicationErrors.CLIENT_NOT_FOUND));

		client.setActive(partialClientDto.isActive());
		return ClientMapper.toDto(clientRepository.save(client));
	}

	@Override
	public void deleteById(Long id) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ApplicationException(ApplicationErrors.CLIENT_NOT_FOUND));

		clientRepository.delete(client);
		outboxService.publish(AggregateTypeEnum.CLIENT, id, EventTypeEnum.CLIENT_DELETED);
	}
}
