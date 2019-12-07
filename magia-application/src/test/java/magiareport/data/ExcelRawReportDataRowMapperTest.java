package magiareport.data;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.zolyomia.magiareport.data.rawreport.ExcelRawReportDataRowMapper;
import com.zolyomia.magiareport.data.rawreport.domain.RawReportDataRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class ExcelRawReportDataRowMapperTest {

    private static final String ID_CELL_VALUE = "ID";
    private static final Date REGISTRATION_DATE_CELL_VALUE = createDate(2019,4,2, 12, 21, 0);
    private static final String DESCRIBE_CELL_VALUE = "DESCRIBE";
    private static final String INFORMATION_CELL_VALUE = "INFORMATION";
    private static final String RESOLUTION_PROCESSS_TATE_CELL_VALUE = "RESOLUTION_PROCESSS_TATE_CELL_VALUE";
    private static final String WORKGROUP_CELL_VALUE = "WORKGROUP_CELL_VALUE";
    private static final String PERSON_CELL_VALUE = "PERSON_CELL_VALUE";
    private static final String STATUS_CELL_VALUE = "STATUS_CELL_VALUE";
    private static final String CATEGORY_CELL_VALUE = "CATEGORY_CELL_VALUE";
    private static final String STARTTIME_CELL_VALUE = "STARTTIME_CELL_VALUE";
    private static final String REPORTER_CELL_VALUE = "REPORTER_CELL_VALUE";
    private static final String REPORTINGID_CELL_VALUE = "REPORTINGID_CELL_VALUE";
    private static final String PRIORITY_CELL_VALUE = "PRIORITY_CELL_VALUE";
    private static final Date SOLUTIONDEADLINE_CELL_VALUE = createDate(2019,4,2, 12, 21, 0);;
    private static final Date ANALYSISDEADLINE_CELL_VALUE = createDate(2019,4,2, 12, 21, 0);;
    private static final String REPORTINGCONFIGURATIONITEM_CELL_VALUE = "REPORTINGCONFIGURATIONITEM_CELL_VALUE";

    private static Date createDate(int year, int month, int day, int hour, int minute, int second) {
        return Date
            .from(LocalDateTime.of(year, month, day, hour, minute, second)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @Mock
    private Row mockRow;

    private ExcelRawReportDataRowMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new ExcelRawReportDataRowMapper();
    }

    @Test
    @DisplayName("")
    void testMapRowShouldWork() {
        when(mockRow.getCell(0)).thenReturn(getMockCell(ID_CELL_VALUE));
        when(mockRow.getCell(1)).thenReturn(getMockCell(REGISTRATION_DATE_CELL_VALUE));
        when(mockRow.getCell(2)).thenReturn(getMockCell(DESCRIBE_CELL_VALUE));
        when(mockRow.getCell(3)).thenReturn(getMockCell(INFORMATION_CELL_VALUE));
        when(mockRow.getCell(4)).thenReturn(getMockCell(RESOLUTION_PROCESSS_TATE_CELL_VALUE));
        when(mockRow.getCell(5)).thenReturn(getMockCell(WORKGROUP_CELL_VALUE));
        when(mockRow.getCell(6)).thenReturn(getMockCell(PERSON_CELL_VALUE));
        when(mockRow.getCell(7)).thenReturn(getMockCell(STATUS_CELL_VALUE));
        when(mockRow.getCell(8)).thenReturn(getMockCell(CATEGORY_CELL_VALUE));
        when(mockRow.getCell(9)).thenReturn(getMockCell(STARTTIME_CELL_VALUE));
        when(mockRow.getCell(10)).thenReturn(getMockCell(REPORTER_CELL_VALUE));
        when(mockRow.getCell(11)).thenReturn(getMockCell(REPORTINGID_CELL_VALUE));
        when(mockRow.getCell(12)).thenReturn(getMockCell(PRIORITY_CELL_VALUE));
        when(mockRow.getCell(13)).thenReturn(getMockCell(SOLUTIONDEADLINE_CELL_VALUE));
        when(mockRow.getCell(14)).thenReturn(getMockCell(ANALYSISDEADLINE_CELL_VALUE));
        when(mockRow.getCell(15)).thenReturn(getMockCell(REPORTINGCONFIGURATIONITEM_CELL_VALUE));

        RawReportDataRow actual = underTest.mapRow(mockRow);


    }

    private Cell getMockCell(String value) {
        Cell mockCell = Mockito.mock(Cell.class);
        when(mockCell.getStringCellValue()).thenReturn(value);
        return mockCell;
    }

    private Cell getMockCell(Date value) {
        Cell mockCell = Mockito.mock(Cell.class);
        when(mockCell.getDateCellValue()).thenReturn(value);
        return mockCell;
    }
}