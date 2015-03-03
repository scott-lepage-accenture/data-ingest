package com.coginitive.interview.processor;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;

public class Neo4JRESTClientProcessor implements Processor {
    public static String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
    private static final Logger LOG = Logger.getLogger(Neo4JRESTClientProcessor.class);
    private String serverURI = SERVER_ROOT_URI;

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMsg = exchange.getIn();

        //csv
        if (inMsg.getBody() instanceof ArrayList) {
            LOG.info("IS ARRAY LIST");
            String body = inMsg.getBody(String.class);
            String[] values = body.split(",");
            URI node = createNode("UniqueId", values[0]);
            if (node != null) {
                //add some properties
                addProperty(node, "FirstName", values[1]);
                addProperty(node, "LastName", values[2]);
                inMsg.setBody(node.toString());
            }
        }
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    private URI createNode(String name, String value) {
        final String nodeEntryPointUri = serverURI + "node";
        // http://localhost:7474/db/data/node
        WebResource resource = Client.create().resource(nodeEntryPointUri);
        // POST {} to the node entry point URI
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .entity(String.format("{\"%s\" : \"%s\"}", name, value))
                .post(ClientResponse.class);
        if (response != null) {
            final URI location = response.getLocation();
            LOG.info(String.format(
                    "POST to [%s], status code [%d], location header [%s]",
                    nodeEntryPointUri, response.getStatus(), location.toString()));
            response.close();
            return location;
        }

        return null;
    }

    private void addProperty(URI nodeUri, String propertyName,
                                    String propertyValue) {
        String propertyUri = nodeUri.toString() + "/properties/" + propertyName;
        // http://localhost:7474/db/data/node/{node_id}/properties/{property_name}

        WebResource resource = Client.create()
                .resource(propertyUri);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .entity("\"" + propertyValue + "\"")
                .put(ClientResponse.class);
        if (response != null) {
            LOG.info(String.format("PUT to [%s], status code [%d]",
                    propertyUri, response.getStatus()));
            response.close();
        }

    }
}
