package com.zolyomia.magiareport.ui.main;

import static java.util.Objects.nonNull;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.zolyomia.magiareport.application.scope.ScreenScoped;
import com.zolyomia.magiareport.service.weeklyreport.WeeklyAgeingReportService;
import com.zolyomia.magiareport.service.weeklyreport.WeeklyRawReportService;
import com.zolyomia.magiareport.ui.base.BaseScreenController;
import com.zolyomia.magiareport.ui.main.domain.WeeklyReportRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ScreenScoped
public class MainController extends BaseScreenController implements Initializable {

    @FXML
    private TableView rawReportTable;

    private final WeeklyRawReportService weeklyRawReportService;
    private final WeeklyAgeingReportService weeklyAgeingReportService;

    @Autowired
    public MainController(WeeklyRawReportService weeklyRawReportService,
        WeeklyAgeingReportService weeklyAgeingReportService) {
        this.weeklyRawReportService = weeklyRawReportService;
        this.weeklyAgeingReportService = weeklyAgeingReportService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sc.setResizable(true);
        this.screenName.setText("main");
    }

    @FXML
    private void handleOpenRowData(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        if (nonNull(file)) {
            ObservableList<WeeklyReportRow> weeklyReportRows = FXCollections.observableArrayList(weeklyRawReportService.findAllFrom(file));
            rawReportTable.setItems(weeklyReportRows);
            rawReportTable.refresh();
        }
    }

    @FXML
    private void handleGenerateAgeingReport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open former Ageing Report File");
        File formerReport = fileChooser.showOpenDialog(null);

        fileChooser.setTitle("Open new Raw Report File");
        File rawReport = fileChooser.showOpenDialog(null);

        if (nonNull(formerReport)) {
            weeklyAgeingReportService.generateWeeklyAgingReport(rawReport, formerReport);
        }
    }
}
