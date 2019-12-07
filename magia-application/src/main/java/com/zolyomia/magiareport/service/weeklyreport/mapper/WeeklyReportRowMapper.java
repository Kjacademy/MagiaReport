package com.zolyomia.magiareport.service.weeklyreport.mapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.zolyomia.magiareport.data.RowMapper;
import com.zolyomia.magiareport.data.rawreport.domain.RawReportDataRow;
import com.zolyomia.magiareport.ui.main.domain.WeeklyReportRow;
import org.springframework.stereotype.Component;

@Component
public class WeeklyReportRowMapper implements RowMapper<WeeklyReportRow, RawReportDataRow> {

    private static final String ONE_TO_SEVEN = "0-7 days";
    private static final String EIGHT_TO_THIRTY = "8-30 days";
    private static final String THIRTYONE_TO_NINETY = "31-90 days";
    private static final String OLDER_THAN_NINETY = "90 days older";

    @Override
    public WeeklyReportRow mapRow(RawReportDataRow rawReportDataRow) {
        return WeeklyReportRow.builder()
            .id(rawReportDataRow.getId())
            .registrationDate(rawReportDataRow.getRegistrationDate())
            .ageCategory(mapAgeCategory(rawReportDataRow))
            .numberOfDays(calculateNumberOfDays(rawReportDataRow))
            .describe(rawReportDataRow.getDescribe())
            .information(rawReportDataRow.getInformation())
            .resolutionProcessState(rawReportDataRow.getResolutionProcessState())
            .workgroup(rawReportDataRow.getWorkgroup())
            .person(rawReportDataRow.getPerson())
            .status(rawReportDataRow.getStatus())
            .category(rawReportDataRow.getCategory())
            .startTime(rawReportDataRow.getStartTime())
            .reporter(rawReportDataRow.getReporter())
            .reportingId(rawReportDataRow.getReportingId())
            .priority(rawReportDataRow.getPriority())
            .solutionDeadline(rawReportDataRow.getSolutionDeadline())
            .analysisDeadline(rawReportDataRow.getAnalysisDeadline())
            .reportingConfigurationItem(rawReportDataRow.getReportingConfigurationItem())
            .build();
    }

    private String mapAgeCategory(RawReportDataRow rawReportDataRow) {
        long numberOfDays = calculateNumberOfDays(rawReportDataRow);
        if (numberOfDays < 8) {
            return ONE_TO_SEVEN;
        } else if (numberOfDays <= 30) {
            return EIGHT_TO_THIRTY;
        } else if (numberOfDays <= 90) {
            return THIRTYONE_TO_NINETY;
        } else {
            return OLDER_THAN_NINETY;
        }
    }

    private long calculateNumberOfDays(RawReportDataRow rawReportDataRow) {
        return rawReportDataRow.getRegistrationDate().until(LocalDateTime.now(), ChronoUnit.DAYS);
    }
}
