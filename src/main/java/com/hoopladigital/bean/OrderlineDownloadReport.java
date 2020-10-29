package com.hoopladigital.bean;

import com.hoopladigital.annotation.ReportType;
import lombok.Data;

@Data
@ReportType("ORDER_LINE_DOWNLOAD")
public class OrderlineDownloadReport {

    private String name;
    private String id;
    private Integer[] maybeAnArray;
}
