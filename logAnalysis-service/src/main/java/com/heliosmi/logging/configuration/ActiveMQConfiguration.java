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
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class to initialize ActiveMQ beans.
 * 
 * @author Saurabh Maheshwari
 * 
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:app.properties")
@ComponentScan("com.heliosmi.logging.listener")
@Import(HibernateConfiguration.class)
public class ActiveMQConfiguration {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private MessageListener logListener;

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

    @Bean
    public ConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                env.getProperty("activeMQ.brokerURL"));
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);

        return pooledConnectionFactory;
    }

    @Bean
    public Destination activeMQDestination() {
        ActiveMQQueue destination = new ActiveMQQueue(env.getProperty("activeMQ.logQueue"));
        return destination;
    }

    @Bean
    public PlatformTransactionManager jmsTransactionManagerBean() {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager(activeMQConnectionFactory());
        return jmsTransactionManager;
    }

}
