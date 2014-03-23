package com.heliosmi.logging.listener;

import static org.junit.Assert.*;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
    
    @Test
    public void testOnMessage() throws JMSException{
        LogListener logListener = new LogListener();
        
        ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
        activeMQMapMessage.setString("applicationName", "logAnalysis");
        
        SessionFactory sessionFactory = mock(SessionFactory.class);
        when(sessionFactory.getCurrentSession()).thenReturn(null);
        logListener.setSessionFactory(sessionFactory);
     
        logListener.onMessage(activeMQMapMessage);
        
    }

}
