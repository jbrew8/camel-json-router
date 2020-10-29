package com.hoopladigital.bean;

import lombok.Data;

@Data
public class ReportRequest<REPORT_TYPE> {
    private String reportType;
    private REPORT_TYPE payload;
}