package com.zolyomia.magiareport.data;

import java.io.File;

import com.zolyomia.magiareport.data.rawreport.ExcelRawReportDataRepository;
import com.zolyomia.magiareport.data.rawreport.ExcelRawReportDataRowMapper;
import com.zolyomia.magiareport.data.rawreport.RawReportDataRepository;
import com.zolyomia.magiareport.data.rawreport.exception.RawReportProcessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ExcelRawReportDataRepositoryTest {

    @Mock
    private ExcelRawReportDataRowMapper rowMapper;

    private RawReportDataRepository underTest;

    @BeforeEach
    public void setUp(){
        underTest = new ExcelRawReportDataRepository(rowMapper);
    }

    @Test()
    @DisplayName("ExcelRawReportDataRepository.findAll() should throw exception if the workbook contains more than one sheets")
    public void testRepositoryShouldThrowException() {
        File workbookPath = getFileFromResource("/workbooks/WorkbookWithTwoEmptySheets.xlsx");
        Assertions.assertThrows(RawReportProcessException.class, () -> underTest.findAll(workbookPath));
    }

    @Test()
    @DisplayName("ExcelRawReportDataRepository.findAll() SHOULD NOT throw exception if worksheet's header HAS proper header cell count")
    public void testRepositoryShouldThrowException2() {
        File workbookPath = getFileFromResource("/workbooks/WorkbookWithProperHeaderOnly.xlsx");
        underTest.findAll(workbookPath);
    }

    @Test()
    @DisplayName("ExcelRawReportDataRepository.findAll() SHOULD throw exception if worksheet's header DOES NOT HAVE proper header cell count")
    public void testRepositoryShouldThrowException3() {
        File workbookPath = getFileFromResource("/workbooks/WorkbookWithOneEmptySheet.xlsx");
        Assertions.assertThrows(RawReportProcessException.class, () -> underTest.findAll(workbookPath));
    }

    private File getFileFromResource(String path) {
        return new File(getClass().getResource(path).getFile());
    }
}