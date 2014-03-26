package com.heliosmi.logging.listener;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heliosmi.logging.data.LogMessage;
import com.heliosmi.logging.entity.LogMessageEntity;
import com.heliosmi.logging.util.TestDataFactory;

import static org.mockito.Mockito.*;

@ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LogListenerTest {

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
    /*@Test
    public void testOnMessage() throws JMSException {
        LogListener logListener = new LogListener();

        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
        activeMQMapMessage.setString("applicationName", "logAnalysis");

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(new LogMessageEntity())).thenReturn(null);

        logListener.setSessionFactory(sessionFactory);

        logListener.onMessage(activeMQMapMessage);
    }*/

    /**
     * Tests a successful execution of onMesage.
     *  
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JMSException
     */
    @Test
    public void testOnMessageComplete() throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, JMSException {
        LogMessage logMessage = TestDataFactory.createLogMessage();
        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();

        Map<String, String> k = BeanUtils.describe(logMessage);
        for (String key : k.keySet()) {
            activeMQMapMessage.setString(key, BeanUtils.getSimpleProperty(logMessage, key));
        }

        logListener.onMessage(activeMQMapMessage);
    }

}
