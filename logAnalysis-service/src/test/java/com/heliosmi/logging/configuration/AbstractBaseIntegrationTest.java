package com.heliosmi.logging.configuration;

import org.apache.activemq.broker.BrokerService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Base class for all Integration tests. It will initialize the Spring
 * container.
 * 
 * Sets up embedded ActiveMQ broker for use in Integration Tests.
 * 
 * @author Saurabh Maheshwari
 * 
 */
// @ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, MVCConfig.class, TestEnv.class, ProdEnv.class })
@ActiveProfiles("dev")
public abstract class AbstractBaseIntegrationTest {

    /** Logger available to subclasses */
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    private BrokerService broker = new BrokerService();

    @Before
    public void setUp() throws Exception {
        broker.addConnector("tcp://localhost:61617");
        broker.start();
    }

    @After
    public void tearDown() throws Exception {
        broker.deleteAllMessages();
        defaultMessageListenerContainer.stop();
        broker.stop();
    }
}
