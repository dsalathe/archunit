package ch.brownbag.layered.service;

import ch.brownbag.layered.persistence.ArchangelsRepository;
import ch.brownbag.layered.persistence.domain.Archangel;
import ch.brownbag.layered.service.dto.ArchangelDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArchangelsService {
    private ArchangelsRepository repository;

    public ArchangelsService(ArchangelsRepository repository) {
        this.repository = repository;
    }

    public List<ArchangelDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ArchangelDTO> findByName(String name) {
        return repository.findByName(name)
                .map(this::toDTO);
    }

    private ArchangelDTO toDTO(Archangel archangel) {
        return new ArchangelDTO(archangel.name(), archangel.role(), archangel.feastDay());
    }
}
