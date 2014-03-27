package com.heliosmi.logging.listener;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.heliomi.logging.util.ServiceTestDataFactory;
import com.heliosmi.logging.configuration.BaseIntegration;
import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.util.ClientTestDataFactory;

public class LogListenerTest extends BaseIntegration {

    @Autowired
    private MessageListener logListener;

    @Test
    public void testconfig() {
        assertNotNull(logListener);
    }

    /**
     * Tests a successful execution of onMesage by mocking out sessionFactory. *
     * 
     * @throws JMSException
     */
    /*
     * @Test public void testOnMessage() throws JMSException { LogListener
     * logListener = new LogListener();
     * 
     * ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
     * activeMQMapMessage.setString("applicationName", "logAnalysis");
     * 
     * SessionFactory sessionFactory = mock(SessionFactory.class); Session
     * session = mock(Session.class);
     * when(sessionFactory.getCurrentSession()).thenReturn(session);
     * when(session.save(new LogMessageEntity())).thenReturn(null);
     * 
     * logListener.setSessionFactory(sessionFactory);
     * 
     * logListener.onMessage(activeMQMapMessage); }
     */

    /**
     * Tests a successful execution of onMesage.
     * 
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JMSException
     */
    @Test
    public void testOnMessageSuccessful() throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, JMSException {
        ActiveMQMapMessage activeMQMapMessage = ServiceTestDataFactory.createActiveMQMapMessage();
        logListener.onMessage(activeMQMapMessage);
    }    

}
