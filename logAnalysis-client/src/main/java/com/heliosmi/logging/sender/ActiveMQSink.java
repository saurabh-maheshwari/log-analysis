package com.heliosmi.logging.sender;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.heliosmi.logging.data.LogMessage;

/**
 * Helper class to send message to ActiveMQ queue. It initialize
 * {@link JmsTemplate} to call message broker.
 * 
 * @author Saurabh Maheshwari
 * 
 */
public class ActiveMQSink {
    private Logger log = LoggerFactory.getLogger(getClass());
    private JmsTemplate jmsTemplate;

    /**
     * Create a new ActiveMQSink with provided ActiveMQ specific brokerURL and
     * queue.
     * 
     * @param brokerURL
     * @param destinationQueue
     */
    public ActiveMQSink(String brokerURL, String destinationQueue) {
        Validate.notEmpty(brokerURL);
        Validate.notEmpty(destinationQueue);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(destinationQueue);

        jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(activeMQQueue);
    }

    /**
     * Send the logMessage to specified brokerURL and queue.
     * 
     * <p>
     * Converts the logMessage to Map using BeanUtils.
     * 
     * @param logMessage
     */
    public void sendLogMessage(final LogMessage logMessage) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                try {
                    Map<String, String> k = BeanUtils.describe(logMessage);
                    for (String key : k.keySet()) {
                        message.setString(key, BeanUtils.getSimpleProperty(logMessage, key));
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
                    log.error(ExceptionUtils.getMessage(exception));
                }

                return message;
            }
        });
    }

}
