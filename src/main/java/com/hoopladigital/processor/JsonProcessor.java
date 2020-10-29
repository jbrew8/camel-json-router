package com.hoopladigital.processor;

import com.hoopladigital.bean.OrderlineDownloadReport;
import com.hoopladigital.bean.ReportRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("jsonProcessor")
public class JsonProcessor {
    public void process(final ReportRequest<OrderlineDownloadReport> request) {
        log.info("JSON Processor received {}", request);
    }
}
