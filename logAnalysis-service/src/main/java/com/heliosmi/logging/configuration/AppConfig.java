package com.heliosmi.logging.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Base Configuration class to initialize ActiveMQ beans. Also scans the base-package
 * to pull other beans. 
 * <p>This class should be initialized after
 * {@link HibernateConfig}, since <code>transactionManager</code> defined
 * in {@link HibernateConfig} is used for persistence.
 * 
 * @author Saurabh Maheshwari
 * 
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.heliosmi.logging")
@Import(HibernateConfig.class)
@EnableAspectJAutoProxy
public class AppConfig {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EnvConfig env;

    @Autowired
    private MessageListener logListener;

    /**
     * Initializes container to process messages from ActiveMQ broker. Broker
     * settings are pulled from <code>app.properties</code> file.
     * 
     * @return defaultMessageListenerContainer
     */
    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer() {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(activeMQConnectionFactory());
        defaultMessageListenerContainer.setDestination(activeMQDestination());
        defaultMessageListenerContainer.setMessageListener(logListener);
        defaultMessageListenerContainer.setConcurrency(env.getProperty("activeMQ.maxConcurrentConsumers"));
        defaultMessageListenerContainer.setTransactionManager(jmsTransactionManagerBean());

        return defaultMessageListenerContainer;
    }

    /**
     * Create a {@link PooledConnectionFactory} to ActiveMQ broker. Connection
     * settings are pulled from <code>app.properties</code> file.
     * 
     * @return pooledConnectionFactory
     */
    @Bean
    public ConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                env.getProperty("activeMQ.brokerURL"));
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);

        return pooledConnectionFactory;
    }

    /**
     * Create a queue destination to pull messages from. Queue name is pulled
     * from <code>app.properties</code> file.
     * 
     * @return destination
     */
    @Bean
    public Destination activeMQDestination() {
        ActiveMQQueue destination = new ActiveMQQueue(env.getProperty("activeMQ.logQueue"));
        return destination;
    }

    /**
     * Create a {@link JmsTransactionManager} to be used for
     * <code>defaultMessageListenerContainer</code>
     * 
     * @return jmsTransactionManagerBean
     */
    @Bean
    public PlatformTransactionManager jmsTransactionManagerBean() {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager(activeMQConnectionFactory());
        return jmsTransactionManager;
    }

}
