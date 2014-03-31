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
                    ActiveMQSink logSender = new ActiveMQSink(brokerURL, destinationQueue);
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
        ActiveMQSink logSender = ClientTestDataFactory.createLocalLogSender();
        LogMessage logMessage = new LogMessage.Builder().applicationName("applicationName").build();
        logSender.sendLogMessage(logMessage);

    }

}
