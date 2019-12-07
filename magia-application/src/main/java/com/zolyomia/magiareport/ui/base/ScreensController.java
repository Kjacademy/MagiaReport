package com.zolyomia.magiareport.ui.base;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.util.*;

import com.zolyomia.magiareport.application.scope.ScreenScope;
import com.zolyomia.magiareport.application.scope.ScreenScoped;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ScreensController implements ApplicationContextAware {

    private ScreenScope screenScope;

    private ApplicationContext applicationContext;
    private Stage stage;
    private String currentScreenId;
    private final Map<String, BaseScreenController> screens = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    public ScreensController(ScreenScope screenScope) {
        this.screenScope = screenScope;
    }

    public void init(Stage stage) {
        this.stage = stage;
        StackPane root = new StackPane();
        this.stage.setScene(new Scene(root));
    }

    public void loadScreen(String fxml) {
        BaseScreenController oldScreenController = this.getCurrentController();
        try {
            Class controllerClass = FXMLUtils.getControllerClass(fxml);
            final BaseScreenController fxmlController = (BaseScreenController) applicationContext.getBean(controllerClass);

            if (isNull(this.screens.get(fxmlController.getScreenId()))) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                loader.setControllerFactory((aClass) -> fxmlController);
                Parent root = loader.load();
                fxmlController.setRoot(root);
                this.screens.put(fxmlController.getScreenId(), fxmlController);
            }

            this.currentScreenId = fxmlController.getScreenId();
            swapScreen(fxmlController.getRoot());
            if (oldScreenController != null) {
                if (oldScreenController.getClass().isAnnotationPresent(ScreenScoped.class)) {
                    this.screens.remove(oldScreenController.getScreenId());
                    this.screenScope.remove(oldScreenController.getScreenId());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void swapScreen(final Parent root) {
        final Pane rootGroup = getScreenRoot();
        final DoubleProperty opacity = rootGroup.opacityProperty();

        if (!isScreenEmpty()) {
            Timeline fade = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                new KeyFrame(new Duration(500), (actionEvent) -> {
                    rootGroup.getChildren().remove(0);
                    rootGroup.getChildren().add(0, root);
                    Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0))
                    );
                    fadeIn.play();
                },
                    new KeyValue(opacity, 0.0)
                )
            );
            fade.play();
        } else {
            opacity.set(0.0);
            rootGroup.getChildren().add(0, root);
            Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0))
            );
            fadeIn.play();
        }

        if (!this.stage.isShowing()) {
            this.stage.show();
        }
        resizeStage(root);
    }

    private void resizeStage(Parent root) {
        if(root instanceof Pane) {
            Pane rootPane = (Pane) root;
            double expectedHeight = rootPane.getPrefHeight() + 50;
            double expectedWidth = rootPane.getPrefWidth() + 50;



            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    if (stage.getHeight() < expectedHeight) {
                        stage.setHeight(stage.getHeight() + 1);
                    }
                    if (stage.getWidth() < expectedWidth) {
                        stage.setWidth(stage.getWidth() + 1);
                    }
                    if(stage.getHeight() >= expectedHeight && stage.getWidth() >= expectedWidth) {
                        this.cancel();
                    }
                }

            }, 1, 5);
        }
    }

    private StackPane getScreenRoot() {
        return (StackPane) this.stage.getScene().getRoot();
    }

    private boolean isScreenEmpty() {
        return getScreenRoot().getChildren().isEmpty();
    }

    private BaseScreenController getCurrentController() {
        return this.screens.get(currentScreenId);
    }

    public void setResizable(boolean value) {
        this.stage.setResizable(value);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
