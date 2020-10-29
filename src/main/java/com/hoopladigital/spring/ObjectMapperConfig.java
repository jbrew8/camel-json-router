package com.hoopladigital.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hoopladigital.bean.ReportRequest;
import com.hoopladigital.jackson.ReportRequestDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

	@Bean
	public ObjectMapper jsonReportObjectMapper() {

		final SimpleModule module = new SimpleModule();
		module.addDeserializer(ReportRequest.class, new ReportRequestDeserializer(ReportRequest.class));

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(module);

		return objectMapper;

	}

}
