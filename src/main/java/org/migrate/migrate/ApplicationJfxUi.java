package org.migrate.migrate;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationJfxUi {

    public static void main(String[] args) {
        Application.launch(Migrate.class, args);
    }

}
