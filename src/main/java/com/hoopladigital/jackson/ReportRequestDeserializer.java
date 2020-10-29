package com.hoopladigital.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoopladigital.annotation.ReportType;
import com.hoopladigital.bean.ReportRequest;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
Idea part 1: fetch the report type from the raw json, then use that to fetch type from map
Idea part 2: use annotations to create the map

 */

public class ReportRequestDeserializer extends StdDeserializer<ReportRequest> {

    private ClassPathScanningCandidateComponentProvider classScanner;

    private static Map<String, String> typeMap;

    public ReportRequestDeserializer(Class<?> vc) {
        super(vc);
        populateTypeMap();
        this.classScanner = classScanner;
    }

   /* public ReportRequestDeserializer(JavaType valueType) {
        super(valueType);
    }

    public ReportRequestDeserializer(StdDeserializer<?> src) {
        super(src);
    }*/

    @Override
    public ReportRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {


        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final String reportType = node.get("reportType").asText();

        final String reportClass = typeMap.get(reportType);

        final ReportRequest reportObj = new ReportRequest<>();
        reportObj.setReportType(reportType);

        final JsonParser payloadParser = node.get("payload").traverse();
        // deserializng payload fails with "unexpected end of input" if we don't call nextToken() first
        payloadParser.nextToken();

        try {
            final Class type = Class.forName(reportClass);
            reportObj.setPayload(deserializationContext.readValue(payloadParser, type));

        } catch (final ClassNotFoundException e) {
            throw new RuntimeException("Unable to deserialize report payload");
        }

        return reportObj;
    }

    private void populateTypeMap() {

        typeMap = new HashMap<>();

        // add any class with the @ReportType annotation to the type map
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(ReportType.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("com.hoopladigital.bean")) {

            final String reportClass = bd.getBeanClassName();
            final String reportType;
            try {
                 reportType = Class.forName(reportClass).getAnnotation(ReportType.class).value();
            } catch (final ClassNotFoundException e) {
                throw new RuntimeException("unable to fetch class info for " + reportClass);
            }

            typeMap.put(reportType, reportClass);

            System.out.println(bd.getBeanClassName());
        }
    }
}
