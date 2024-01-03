package ch.brownbag.layered.presentation;

import ch.brownbag.layered.service.ArchangelsService;
import ch.brownbag.layered.service.dto.ArchangelDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/archangels", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ArchangelsController {
    private final ArchangelsService archangels;

    public ArchangelsController(ArchangelsService archangels) {
        this.archangels = archangels;
    }

    @GetMapping
    public List<ArchangelDTO> getArchangels() {
        return archangels.findAll();
    }

    @GetMapping("/{name}")
    public ArchangelDTO getArchangelByName(@PathVariable String name) {
        return archangels.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("unknown archangel: " + name));
    }

}
