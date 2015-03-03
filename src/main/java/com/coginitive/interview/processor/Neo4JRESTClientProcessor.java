package com.coginitive.interview.processor;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class Neo4JRESTClientProcessor implements Processor {
    public static String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
    private static final Logger LOG = Logger.getLogger(Neo4JRESTClientProcessor.class);

    private String serverURI = SERVER_ROOT_URI;

    @Override
    public void process(Exchange exchange) throws Exception {
        WebResource resource = Client.create()
                .resource(serverURI);
        ClientResponse response = null;
        try {
            response = resource.get(ClientResponse.class);
        } catch (ClientHandlerException ex) {
            LOG.warn(ex);
        }
        if (response != null) {
            LOG.info(String.format("GET on [%s], status code [%d]",
                    serverURI, response.getStatus()));
            response.close();
        }
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }
}
