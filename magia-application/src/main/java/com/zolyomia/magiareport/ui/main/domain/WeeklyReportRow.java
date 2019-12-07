package com.zolyomia.magiareport.ui.main.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeeklyReportRow {
    private String id;
    private LocalDateTime registrationDate;
    private Long numberOfDays;
    private String ageCategory;
    private String describe;
    private String information;
    private String resolutionProcessState;
    private String workgroup;
    private String person;
    private String status;
    private String category;
    private String startTime;
    private String reporter;
    private String reportingId;
    private String priority;
    private LocalDateTime solutionDeadline;
    private LocalDateTime analysisDeadline;
    private String reportingConfigurationItem;
}
