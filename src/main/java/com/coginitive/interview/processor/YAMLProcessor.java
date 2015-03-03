package com.coginitive.interview.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class YAMLProcessor implements Processor {
    private static final Logger LOG = Logger.getLogger(YAMLProcessor.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String yamlMsg = exchange.getIn().getBody(String.class);
        Map<String,String> out = new HashMap<String, String>();
        exchange.getIn().setBody(mapper.readValue(yamlMsg,Map.class).toString());
    }
}
