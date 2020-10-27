package com.hoopladigital.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("jsonProcessor")
public class JsonProcessor {

    private static final Logger log = LoggerFactory.getLogger(JsonProcessor.class);

    public void process(final Object obj) {

        log.info("JSON Processor received {}", obj);

    }

}
