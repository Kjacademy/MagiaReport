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
public class LoginController extends BaseScreenController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sc.setResizable(false);
        this.screenName.setText("login");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        this.sc.loadScreen("/fxml/main.fxml");
    }
}
