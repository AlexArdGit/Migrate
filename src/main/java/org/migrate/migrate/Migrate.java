package org.migrate.migrate;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.migrate.migrate.jfxui.StageReadyEvent;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class Migrate extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {

        ApplicationContextInitializer<GenericApplicationContext> initializer = appContext -> {
            appContext.registerBean(Application.class, () -> Migrate.this);
            appContext.registerBean(Parameters.class, this::getParameters);
            appContext.registerBean(HostServices.class, this::getHostServices);
        };

        this.context = new SpringApplicationBuilder()
                .sources(ApplicationJfxUi.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        this.context.stop();
        Platform.exit();
    }

}
