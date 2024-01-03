package ch.brownbag.onion.application.service;




import ch.brownbag.onion.application.service.dto.ArchangelDTO;
import ch.brownbag.onion.domain.model.Archangel;
import ch.brownbag.onion.domain.service.ArchangelsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArchangelsService {
    private final ArchangelsRepository repository;

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
