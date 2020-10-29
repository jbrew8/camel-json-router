package com.hoopladigital.processor;

import com.hoopladigital.bean.ReportRequest;
import com.hoopladigital.bean.SomeReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("anotherProcessor")
public class AnotherProcessor {
	public void process(final ReportRequest<SomeReport> request){
		log.info("another processor got {}", request);
	}
}
