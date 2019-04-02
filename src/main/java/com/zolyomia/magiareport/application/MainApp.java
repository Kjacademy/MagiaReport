package com.zolyomia.magiareport.application;

import com.zolyomia.magiareport.application.config.AppContextConfig;
import com.zolyomia.magiareport.ui.controller.base.ScreensController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppContextConfig.class);
        ScreensController bean = context.getBean(ScreensController.class);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("Magia Report 1.0");
        bean.init(stage);

        bean.loadScreen("/fxml/login.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
