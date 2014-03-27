package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

public class AppConfigurationTest extends BaseIntegration {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTransactionManager transactionManager;

    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    @Test
    public void testConfig() {
        assertNotNull(sessionFactory);
        assertNotNull(transactionManager);
        assertNotNull(defaultMessageListenerContainer);
    }

}
