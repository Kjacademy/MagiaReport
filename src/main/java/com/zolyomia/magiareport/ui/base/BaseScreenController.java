package com.zolyomia.magiareport.ui.base;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseScreenController implements Initializable, BeanNameAware {
    private String screenId;
    @Autowired
    protected ScreensController sc;
    private Parent root;

    @FXML
    protected Label screenName;

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    @Override
    public void setBeanName(String beanName) {
        this.screenId = beanName;
    }

}
