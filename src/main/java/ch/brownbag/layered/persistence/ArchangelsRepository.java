package ch.brownbag.layered.persistence;

import ch.brownbag.layered.persistence.domain.Archangel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArchangelsRepository {

    private static final Map<String, Archangel> archangels = Map.of(
            "Michael", new Archangel("Michael", "Defender of Heaven", "September 29"),
            "Gabriel", new Archangel("Gabriel", "Messenger of God", "March 24"),
            "Raphael", new Archangel("Raphael", "Healer and Guide", "September 29"),
            "Uriel", new Archangel("Uriel", "Bearer of Light", "July 28"),
            "Selaphiel", new Archangel("Selaphiel", "Guardian of Prayer", "February 10"),
            "Raguel", new Archangel("Raguel", "Dispenser of Justice", "September 29"),
            "Remiel", new Archangel("Remiel", "Angel of Hope", "October 2")
    );

    public List<Archangel> findAll() {
        return archangels.values().stream().toList();
    }

    public Optional<Archangel> findByName(String name) {
        return Optional.ofNullable(archangels.get(name));
    }
}
