package com.cognitive.interview;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by u27565 on 2/28/15.
 */
public class DateIngestRouteTest extends CamelBlueprintTestSupport {

    public DateIngestRouteTest() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public boolean isUseAdviceWith() {
        // tell we are using advice with, which allows us to advice the route
        // before Camel is being started, and thus can replace activemq with
        // something else.
        return true;
    }

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint.xml";
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDataIngest() throws Exception{
        getMockEndpoint("mock:result").expectedMessageCount(4);
        getMockEndpoint("mock:exception").expectedMessageCount(0);
        assertMockEndpointsSatisfied();
    }

}
