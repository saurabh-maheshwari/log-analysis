package com.heliosmi.logging.sender;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.activemq.broker.BrokerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.util.ClientTestDataFactory;

/**
 * Creates an embedded ActiveMQ broker for testing.
 * 
 * @author Saurabh Maheshwari
 * 
 */
public class ActiveMQSinkTest {
    private Logger log = LoggerFactory.getLogger(getClass());

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
        broker.stop();
    }

    /**
     * ActiveMQSink constructor should throw an error if null/blank values were
     * passed to it.
     */
    @Test
    public void testNullValueConstructor() {
        String[] strArray = new String[] { null, new String() };
        for (String brokerURL : strArray) {
            for (String destinationQueue : strArray) {
                try {
                    ActiveMQSink activeMQSink = new ActiveMQSink(brokerURL, destinationQueue);
                    fail("test should fail due to null/blank values");
                } catch (NullPointerException | IllegalArgumentException exception) {
                    assertNotNull(exception);
                }
            }
        }
    }

    /**
     * Integration test to send data to embedded broker.
     */
    @Test
    public void testSuccesfulSendLogMessage() {
        ActiveMQSink activeMQSink = ClientTestDataFactory.createLocalActiveMQSink();
        LogMessage logMessage = new LogMessage.Builder().applicationName("applicationName").build();
        activeMQSink.sendLogMessage(logMessage);

    }
    
    /**
     * Integration test to send data to embedded broker.
     */
    /*@Test
    public void testLoad() {
        ActiveMQSink activeMQSink = new ActiveMQSink("tcp://192.168.0.9:61616", "LogMessages.Q");
        LogMessage logMessage = ClientTestDataFactory.createLogMessage();
        for (int i = 0; i < 10000; i++) {
            log.info("index: " + i);
            activeMQSink.sendLogMessage(logMessage);
        }

    }*/

}
