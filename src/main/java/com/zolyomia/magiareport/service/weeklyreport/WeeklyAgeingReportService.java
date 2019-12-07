package com.zolyomia.magiareport.service.weeklyreport;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.zolyomia.magiareport.ui.main.domain.WeeklyReportRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeeklyAgeingReportService {
    private static final String WEEK_PIVOT_SHEET_NAME_PREFIX = "Week";
    private static final String WEEK_RAW_DATA_SHEET_NAME_PREFIX = "Raw list";
    private static final Path LOCAL_REPORTS_PATH = Paths.get("reports");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final WeeklyRawReportService weeklyRawReportService;

    @Autowired
    public WeeklyAgeingReportService(WeeklyRawReportService weeklyRawReportService) {
        this.weeklyRawReportService = weeklyRawReportService;
    }

    public void generateWeeklyAgingReport(File newRawReport, File formerAgeingReport) {
        Workbook weeklyAgeingReport = null;
        try {
            File file = createLocalCopy(formerAgeingReport);
            weeklyAgeingReport = WorkbookFactory.create(file);
            deleteOutdatedSheets(weeklyAgeingReport);

            Sheet rawListSheet = weeklyAgeingReport.createSheet(WEEK_RAW_DATA_SHEET_NAME_PREFIX);
            List<WeeklyReportRow> weeklyReportRows = weeklyRawReportService.findAllFrom(newRawReport);
            //fillSheet();

            FileOutputStream fileOut = new FileOutputStream(getNewReportName() + "xlsx");
            weeklyAgeingReport.write(fileOut);
            fileOut.close();

            weeklyAgeingReport.close();
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private File createLocalCopy(File formerAgeingReport) {
        File result = null;
        try {
            if (!Files.exists(LOCAL_REPORTS_PATH)) {
                Files.createDirectory(LOCAL_REPORTS_PATH);
            }
            Path copied = Paths.get(getNewReportName());
            Files.copy(formerAgeingReport.toPath(), copied, StandardCopyOption.REPLACE_EXISTING);
            result = copied.toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getNewReportName() {
         return LOCAL_REPORTS_PATH + File.separator + "AgeingReport_" + LocalDateTime.now().format(formatter);
    }

    private void deleteOutdatedSheets(Workbook weeklyAgeingReport) {
        int rawListSheetIndex = getOldestSheetIndexByPrefix(weeklyAgeingReport, WEEK_RAW_DATA_SHEET_NAME_PREFIX);
        weeklyAgeingReport.removeSheetAt(rawListSheetIndex);

        int weekListSheetIndex = getOldestSheetIndexByPrefix(weeklyAgeingReport, WEEK_PIVOT_SHEET_NAME_PREFIX);
        weeklyAgeingReport.removeSheetAt(weekListSheetIndex);

    }

    private int getOldestSheetIndexByPrefix(Workbook weeklyAgeingReport, String namePrefix) {
        int sheetWeekNumber = Integer.MAX_VALUE;
        int result = -1;
        int index = 0;
        for (Sheet sheet : weeklyAgeingReport) {
            if (nonNull(sheet.getSheetName()) && sheet.getSheetName().contains(namePrefix)) {
                int actualSheetWeekNumber = Integer.parseInt(sheet.getSheetName().substring(sheet.getSheetName().length() - 2));
                if (actualSheetWeekNumber < sheetWeekNumber) {
                    sheetWeekNumber = actualSheetWeekNumber;
                    result = index;
                }
            }
            index++;
        }
        return result;
    }
}
