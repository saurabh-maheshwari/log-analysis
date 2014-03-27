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

public class LogSenderTest {
    private Logger log = LoggerFactory.getLogger(getClass());

    // Set up an embedded ActiveMQ broker
    private BrokerService broker = new BrokerService();

    @Before
    public void setUp() throws Exception {
        // configure the broker
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }

    @After
    public void tearDown() throws Exception {
        broker.deleteAllMessages();
        broker.stop();
    }

    /**
     * LogSender constructor should throw an error if null/blank values were
     * passed to it.
     */
    @Test
    public void testNullValueConstructor() {
        String[] strArray = new String[] { null, new String() };
        for (String brokerURL : strArray) {
            for (String destinationQueue : strArray) {
                try {
                    LogSender logSender = new LogSender(brokerURL, destinationQueue);
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
        String brokerURL = "vm://localhost:61616";
        String destinationQueue = "LogMessages.Q";
        LogSender logSender = new LogSender(brokerURL, destinationQueue);
        LogMessage logMessage = new LogMessage.Builder().applicationName("applicationName").build();
        logSender.sendLogMessage(logMessage);
    }

    /*
     * TODO persist data locally in case of failure.
     * 
     * @Test public void testUnsuccesfulSendLogMessage() { String brokerURL =
     * "tcp://192.168.0.10:61616"; String destinationQueue = "dummyQ"; LogSender
     * logSender = new LogSender(brokerURL, destinationQueue); LogMessage
     * logMessage = new
     * LogMessage.Builder().applicationName("applicationName").build();
     * 
     * log.info("Starting transaction"); try {
     * logSender.sendLogMessage(logMessage); } catch (Exception e) { // TODO
     * Auto-generated catch block log.error(ExceptionUtils.getStackTrace(e)); }
     * }
     */

}
