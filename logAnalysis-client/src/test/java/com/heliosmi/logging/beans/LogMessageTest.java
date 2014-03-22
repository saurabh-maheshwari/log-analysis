package com.heliosmi.logging.beans;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.heliosmi.logging.data.LogMessage;

public class LogMessageTest {

    @Test
    public void testCreation() {
        LogMessage logMessage = new LogMessage.Builder()
        .threadID("threadName")
        .className("className")
        .methodName("methodName")
        .applicationName("applicationName")
        .hostName("hostName")
        .duration(3)
        .request("request")
        .packageName("packageName")
        .response("response")
        .errorYN(Boolean.FALSE)
        .errorStacktrace("errorStacktrace").build();

        assertNotNull(logMessage.getCreatedDate());
        assertTrue(logMessage.getDuration() == 3);
        assertTrue(logMessage.getThreadID().equals("threadName"));
        assertTrue(logMessage.getClassName().equals("className"));
        assertTrue(logMessage.getMethodName().equals("methodName"));
        assertTrue(logMessage.getApplicationName().equals("applicationName"));
        assertTrue(logMessage.getHostName().equals("hostName"));
        assertTrue(logMessage.getRequest().equals("request"));
        assertTrue(logMessage.getPackageName().equals("packageName"));
        assertTrue(logMessage.getResponse().equals("response"));
        assertFalse(logMessage.isErrorYN());
        assertTrue(logMessage.getErrorStacktrace().equals("errorStacktrace"));
    }

    @Test
    public void testTruncate_Request_Response_ErrorStacktrace() {

        LogMessage logMessage = new LogMessage.Builder().request(RandomStringUtils.random(5000))
                .response(RandomStringUtils.random(5000)).errorStacktrace(RandomStringUtils.random(5000)).build();

        assertTrue(logMessage.getRequest().length() == 1000);
        assertTrue(logMessage.getResponse().length() == 1000);
        assertTrue(logMessage.getErrorStacktrace().length() == 1000);
    }

    @Test
    public void testTruncate_Method() {
        LogMessage logMessage = new LogMessage.Builder().methodName(RandomStringUtils.random(500)).build();

        assertTrue(logMessage.getMethodName().length() == 50);
    }
}
