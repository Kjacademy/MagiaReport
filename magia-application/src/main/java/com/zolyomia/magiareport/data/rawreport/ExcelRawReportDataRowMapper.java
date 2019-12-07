package com.zolyomia.magiareport.data.rawreport;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.zolyomia.magiareport.data.RowMapper;
import com.zolyomia.magiareport.data.rawreport.domain.RawReportDataRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

@Component
public class ExcelRawReportDataRowMapper implements RowMapper<RawReportDataRow, Row> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

    @Override
    public RawReportDataRow mapRow(Row row) {

        return RawReportDataRow.builder()
            .id(mapStringCellValue(row.getCell(0)))
            .registrationDate(mapDateCellValue(row.getCell(1)))
            .describe(mapStringCellValue(row.getCell(2)))
            .information(mapStringCellValue(row.getCell(3)))
            .resolutionProcessState(mapStringCellValue(row.getCell(4)))
            .workgroup(mapStringCellValue(row.getCell(5)))
            .person(mapStringCellValue(row.getCell(6)))
            .status(mapStringCellValue(row.getCell(7)))
            .category(mapStringCellValue(row.getCell(8)))
            .startTime(mapStringCellValue(row.getCell(9)))
            .reporter(mapStringCellValue(row.getCell(10)))
            .reportingId(mapStringCellValue(row.getCell(11)))
            .priority(mapStringCellValue(row.getCell(12)))
            .solutionDeadline(mapDateCellValue(row.getCell(13)))
            .analysisDeadline(mapDateCellValue(row.getCell(14)))
            .reportingConfigurationItem(mapStringCellValue(row.getCell(15)))
            .build();
    }

    private String mapStringCellValue(Cell cell) {
        if(nonNull(cell) && CellType.STRING.equals(cell.getCellTypeEnum())) {
            return Optional.ofNullable(cell)
                .map(Cell::getStringCellValue)
                .orElse(null);
        } else {
            return Optional.ofNullable(cell)
                .map(Cell::getNumericCellValue)
                .map(Object::toString)
                .orElse(null);
        }
    }

    private LocalDateTime mapDateCellValue(Cell cell) {
        return Optional.ofNullable(cell)
            .map(this::mapStringCellValue)
            .map(s -> LocalDateTime.parse(s, FORMATTER))
            .orElse(null);

    }
}
