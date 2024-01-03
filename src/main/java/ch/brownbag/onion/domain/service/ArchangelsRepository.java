package ch.brownbag.onion.domain.service;

import ch.brownbag.onion.application.service.dto.ArchangelDTO;
import ch.brownbag.onion.domain.model.Archangel;

import java.util.List;
import java.util.Optional;

public interface ArchangelsRepository {

    List<Archangel> findAll();

    Optional<Archangel> findByName(String name);

    // Uncomment to assert that onion architecture assessment works well
    //default ArchangelDTO somethingWrong() { return null; }
}
