package com.heliosmi.logging.util;

import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.sender.ActiveMQSink;

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
     * Create a local ActiveMQSink. It will send data to embedded broker within VM. 
     * @return ActiveMQSink
     */
    public static final ActiveMQSink createLocalActiveMQSink(){
        String brokerURL = "vm://localhost:61617";
        String destinationQueue = "LogMessages.Q";
        ActiveMQSink activeMQSink = new ActiveMQSink(brokerURL, destinationQueue);
        return activeMQSink;
    }
}
