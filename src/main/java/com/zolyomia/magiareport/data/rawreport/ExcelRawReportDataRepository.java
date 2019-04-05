package com.zolyomia.magiareport.data.rawreport;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zolyomia.magiareport.data.rawreport.domain.RawReportDataRow;
import com.zolyomia.magiareport.data.rawreport.exception.RawReportProcessException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExcelRawReportDataRepository implements RawReportDataRepository {

    private static final Logger logger = LoggerFactory.getLogger(ExcelRawReportDataRepository.class);
    private static final int FIRST_HEADER_ROW_EXPECTED_CELL_NUMBER = 16;
    private final ExcelRawReportDataRowMapper rowMapper;

    @Autowired
    public ExcelRawReportDataRepository(ExcelRawReportDataRowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<RawReportDataRow> findAll(String datasourcePath) {
        try {
            List<RawReportDataRow> result = new ArrayList<>();

            Workbook workbook = WorkbookFactory.create(new File(datasourcePath));
            checkWorksheetsNumber(workbook);

            Sheet sheet = workbook.getSheetAt(0);
            checkWorksheetHeader(sheet);

            sheet.forEach(row -> {
                if (row.getRowNum() > 0) {
                    result.add(rowMapper.mapRow(row));
                }
            });

            workbook.close();

            return result;

        } catch (IOException e) {
            logger.error("Problem has occurred during the excel data load. File path = {}", datasourcePath, e);
        } catch (InvalidFormatException e) {
            logger.error("Problem has occurred during the excel data load. File path = {}", datasourcePath, e);
        }
        return null;
    }

    private void checkWorksheetHeader(Sheet sheet) {
        if (isNull(sheet.getRow(0))) {
            logger.info("Worksheet is empty!");
            throw new RawReportProcessException(String
                .format("Worksheet is empty! Worksheet should have exactly %d cells in the first(header) row!", FIRST_HEADER_ROW_EXPECTED_CELL_NUMBER));
        }
        if (sheet.getRow(0).getLastCellNum() != FIRST_HEADER_ROW_EXPECTED_CELL_NUMBER) {
            logger.info("Worksheet has " + sheet.getRow(0).getLastCellNum() + " header cells.");
            throw new RawReportProcessException(String.format("Worksheet should have exactly %d header cells!", FIRST_HEADER_ROW_EXPECTED_CELL_NUMBER));
        }
    }

    private void checkWorksheetsNumber(Workbook workbook) {
        if (workbook.getNumberOfSheets() != 1) {
            logger.info("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
            throw new RawReportProcessException("Workbook should have exactly ONE sheet!");
        }
    }
}
