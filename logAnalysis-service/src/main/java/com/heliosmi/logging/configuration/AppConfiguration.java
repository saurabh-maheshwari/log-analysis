package com.heliosmi.logging.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.heliosmi.logging.entity.LogMessageEntity;
import com.heliosmi.logging.listener.LogListener;

/**
 * App Configuration class.
 * <p>
 * Programmatically configure beans instead of using xml.
 * 
 * @author Saurabh Maheshwari
 * 
 */
@Configuration
@PropertySource("classpath:app.properties")
@ComponentScan("com.heliosmi.logging.listener")
// @EnableAspectJAutoProxy
@EnableTransactionManagement
public class AppConfiguration {
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
    public PlatformTransactionManager jmsTransactionManagerBean() {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager(activeMQConnectionFactory());
        return jmsTransactionManager;
    }

    @Bean
    public Destination activeMQDestination() {
        ActiveMQQueue destination = new ActiveMQQueue(env.getProperty("activeMQ.logQueue"));
        return destination;
    }

    @Bean
    public ConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                env.getProperty("activeMQ.brokerURL"));
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);

        return pooledConnectionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws ConfigurationException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactoryBean()
                .getObject());
        return transactionManager;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() throws ConfigurationException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource());
        // bean.setAnnotatedClasses(LogMessageEntity.class);
        bean.setPackagesToScan("com.heliosmi.logging.entity");

        return bean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("jdbc.initialSize")));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("jdbc.maxActive")));

        return dataSource;
    }
}
