package com.zolyomia.magiareport.data.rawreport;

import java.util.List;

import com.zolyomia.magiareport.data.rawreport.domain.RawReportDataRow;

public interface RawReportDataRepository {
    List<RawReportDataRow> findAll(String datasourcePath);
}
