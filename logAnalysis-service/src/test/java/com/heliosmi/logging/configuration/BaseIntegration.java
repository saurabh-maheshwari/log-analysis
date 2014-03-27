package com.heliosmi.logging.configuration;

import org.apache.activemq.broker.BrokerService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for all Integration tests. It will initialize the Spring
 * container.
 * 
 * @author Saurabh Maheshwari
 * 
 */
@ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseIntegration {
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    // Set up an embedded ActiveMQ broker
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
