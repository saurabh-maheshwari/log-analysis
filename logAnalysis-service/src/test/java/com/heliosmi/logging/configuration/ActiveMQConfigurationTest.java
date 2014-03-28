package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.annotation.Transactional;

import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.entity.LogMessageEntity;
import com.heliosmi.logging.sender.ActiveMQSink;
import com.heliosmi.logging.util.ClientTestDataFactory;

public class ActiveMQConfigurationTest extends BaseIntegration {

    @Autowired
    private ActiveMQConfiguration activeMQConfiguration;

    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testConfig() {
        assertNotNull(activeMQConfiguration);
        assertNotNull(defaultMessageListenerContainer);
    }

    /**
     * Complete integration test. Sent data to embedded AciveMQ broker. It
     * was picked by MessageListenerContainer to be persisted in database.
     */
    @Test
    @Transactional
    public void testCompleteLogMessageFlow() {
        LogMessage logMessage = ClientTestDataFactory.createLogMessage();
        ActiveMQSink logSender = ClientTestDataFactory.createLocalLogSender();
        logSender.sendLogMessage(logMessage);

        // check data in DB
        Session session = sessionFactory.getCurrentSession();
        LogMessageEntity logMessageEntity = (LogMessageEntity) session.createQuery(
                "from LogMessageEntity where className='className' ").uniqueResult();

        assertNotNull(logMessageEntity.getId());
        assertTrue(logMessageEntity.getApplicationName().equals("applicationName"));
    }

}
