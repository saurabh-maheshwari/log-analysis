package com.heliosmi.logging.util;

import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.sender.LogSender;

public class ClientTestDataFactory {

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
    
    /**
     * Create a local LogSender. It will send data to embedded broker within VM. 
     * @return LogSender
     */
    public static final LogSender createLocalLogSender(){
        String brokerURL = "vm://localhost:61616";
        String destinationQueue = "LogMessages.Q";
        LogSender logSender = new LogSender(brokerURL, destinationQueue);
        return logSender;
    }
}
