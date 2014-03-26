package com.heliosmi.logging.util;

import com.heliosmi.logging.data.LogMessage;

public class TestDataFactory {

    public static final LogMessage createLogMessage() {
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
        return logMessage;
    }
}
