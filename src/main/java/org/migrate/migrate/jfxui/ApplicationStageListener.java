package org.migrate.migrate.jfxui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
@Slf4j
public class ApplicationStageListener implements ApplicationListener<StageReadyEvent> {


    private final String appTitle;
    private final Resource fxml;
    private final ApplicationContext appContext;

    ApplicationStageListener(@Value("${spring.application.ui.title}") String appTitle,
                             @Value("classpath:/ui.fxml") Resource fxml,
                             ApplicationContext appContext) {
        this.appTitle = appTitle;
        this.fxml = fxml;
        this.appContext = appContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            Stage appUi = stageReadyEvent.getStage();
            URL url = this.fxml.getURL();
            FXMLLoader fxmlloader = new FXMLLoader(url);
            fxmlloader.setControllerFactory(appContext::getBean);
            Parent root = fxmlloader.load();
            Scene scene = new Scene(root, 600, 600);
            appUi.setScene(scene);
            appUi.setTitle(this.appTitle);
            appUi.show();
            log.info("Loaded user interface");
        } catch (IOException e) {
            log.error("Failed to load user interface");
        }
    }

}
