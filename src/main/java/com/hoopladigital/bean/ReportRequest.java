package com.hoopladigital.bean;

import lombok.Data;

@Data
public class ReportRequest<PAYLOAD_TYPE> {
    private final ReportType reportType;
    private PAYLOAD_TYPE payload;
}
