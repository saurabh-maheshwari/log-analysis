package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.heliomi.logging.util.ServiceTestDataFactory;
import com.heliosmi.logging.entity.LogMessageEntity;

public class HibernateConfigTest extends AbstractBaseIntegrationTest {

    @Autowired
    private HibernateConfig hibernateConfiguration;

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
    @Transactional
    public void saveLogMessageEntity() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            JMSException {
        String applicationName = RandomStringUtils.random(10);
        LogMessageEntity logMessageEntity = ServiceTestDataFactory.createLogMessageEntity();
        logMessageEntity.setApplicationName(applicationName);

        Session session = sessionFactory.getCurrentSession();
        session.save(logMessageEntity);
        session.clear();

        LogMessageEntity logMessageEntity2 = (LogMessageEntity) session
                .createQuery("from LogMessageEntity where applicationName= :applicationName ")
                .setString("applicationName", applicationName)
                .uniqueResult();

        assertNotNull(logMessageEntity2.getId());
        assertTrue(logMessageEntity2.getApplicationName().equals(applicationName));
    }

}
