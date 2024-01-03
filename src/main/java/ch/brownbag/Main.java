package ch.brownbag;

import ch.brownbag.layered.persistence.ArchangelsRepository;
import ch.brownbag.layered.presentation.ArchangelsController;
import ch.brownbag.layered.service.ArchangelsService;
import ch.brownbag.onion.adapter.persistence.ArchangelsRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        ArchangelsController controller = new ArchangelsController(new ArchangelsService(new ArchangelsRepository()));
        controller.getArchangels()
                .forEach(archangel -> System.out.println("\uD83D\uDC7C\uD83C\uDFFB " + archangel + " \uD83D\uDC7C\uD83C\uDFFB"));

        System.out.println("Michael's role is " + controller.getArchangelByName("Michael").role());


        ch.brownbag.onion.application.controller.ArchangelsController onionController = new ch.brownbag.onion.application.controller.ArchangelsController(
                new ch.brownbag.onion.application.service.ArchangelsService(
                        new ArchangelsRepositoryImpl()
                )
        );

        System.out.println("Gabriel's role is " + onionController.getArchangelByName("Gabriel").role());
    }
}