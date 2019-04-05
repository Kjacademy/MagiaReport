package com.zolyomia.magiareport.service.weeklyreport;

import static java.util.stream.Collectors.*;

import java.util.List;

import com.zolyomia.magiareport.data.rawreport.RawReportDataRepository;
import com.zolyomia.magiareport.service.weeklyreport.mapper.WeeklyReportRowMapper;
import com.zolyomia.magiareport.ui.main.domain.WeeklyReportRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeeklyRawReportService {

    private final RawReportDataRepository rawReportDataRepository;
    private final WeeklyReportRowMapper rowMapper;

    @Autowired
    public WeeklyRawReportService(RawReportDataRepository rawReportDataRepository,
        WeeklyReportRowMapper rowMapper) {
        this.rawReportDataRepository = rawReportDataRepository;
        this.rowMapper = rowMapper;
    }

    public List<WeeklyReportRow> findAllFrom(String datasourcePath) {
        return rawReportDataRepository
            .findAll(datasourcePath)
            .stream()
            .map(rowMapper::mapRow)
            .collect(toList());
    }

}
