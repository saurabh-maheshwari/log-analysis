package com.heliosmi.logging.sender;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliosmi.logging.beans.LogMessage;

public class LogSenderTest {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * LogSender constructor should throw an error if null/blank values were passed to it.
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

    @Test
    public void testSuccesfulSendLogMessage() {
        String brokerURL = "tcp://192.168.0.9:61616";
        String destinationQueue = "LogMessages.Q";
        LogSender logSender = new LogSender(brokerURL, destinationQueue);
        LogMessage logMessage = new LogMessage.Builder().applicationName("applicationName").build();
        logSender.sendLogMessage(logMessage);
    }

}
