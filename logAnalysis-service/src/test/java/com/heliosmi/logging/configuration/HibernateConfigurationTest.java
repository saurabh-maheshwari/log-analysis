package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.heliomi.logging.util.ServiceTestDataFactory;
import com.heliosmi.logging.entity.LogMessageEntity;

public class HibernateConfigurationTest extends BaseIntegration {

    @Autowired
    private HibernateConfiguration hibernateConfiguration;

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private HibernateTransactionManager transactionManager;
    

    @Test
    public void testConfig() {
        assertNotNull(hibernateConfiguration);
        assertNotNull(sessionFactory);
        assertNotNull(transactionManager);
    }

    /**
     * Integration test to persist <code>LogMessageEntity</code> in database.
     * 
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JMSException
     */
    @Test    
    public void saveLogMessageEntity() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            JMSException {
        LogMessageEntity logMessageEntity = ServiceTestDataFactory.createLogMessageEntity();
        Session session = sessionFactory.getCurrentSession();
        session.save(logMessageEntity);
        session.clear();

        LogMessageEntity logMessageEntity2 = (LogMessageEntity) session.createQuery(
                "from LogMessageEntity where className='className' ").uniqueResult();

        assertNotNull(logMessageEntity2.getId());
        assertTrue(logMessageEntity2.getApplicationName().equals("applicationName"));
    }

}
