package com.zolyomia.magiareport.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.zolyomia.magiareport.application.scope.ScreenScoped;
import com.zolyomia.magiareport.ui.controller.base.BaseScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

@Component
@ScreenScoped
public class MainController extends BaseScreenController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sc.setResizable(true);
        this.screenName.setText("main");
    }

    @FXML
    private void handleSettingOpen(ActionEvent event) {
        this.sc.loadScreen("/fxml/settings.fxml");
    }
}
