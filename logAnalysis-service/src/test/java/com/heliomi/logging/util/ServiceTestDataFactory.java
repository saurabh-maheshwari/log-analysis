package com.heliomi.logging.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.beanutils.BeanUtils;

import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.entity.LogMessageEntity;
import com.heliosmi.logging.util.ClientTestDataFactory;

public class ServiceTestDataFactory {

    /**
     * Creates a dummy {@link ActiveMQMapMessage}
     * 
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JMSException
     */
    public static final ActiveMQMapMessage createActiveMQMapMessage() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, JMSException {
        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
        LogMessage logMessage = ClientTestDataFactory.createLogMessage();

        Map<String, String> k = BeanUtils.describe(logMessage);
        for (String key : k.keySet()) {
            activeMQMapMessage.setString(key, BeanUtils.getSimpleProperty(logMessage, key));
        }
        return activeMQMapMessage;
    }

    /**
     * Creates a dummy {@link LogMessageEntity}
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JMSException
     */
    public static final LogMessageEntity createLogMessageEntity() throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, JMSException {
        LogMessageEntity logMessageEntity = new LogMessageEntity();
        BeanUtils.populate(logMessageEntity, createActiveMQMapMessage().getContentMap());

        return logMessageEntity;
    }
}
