package com.heliosmi.logging.configuration;

import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.heliosmi.logging.entity.LogMessageEntity;

@Configuration
public class AppConfiguration {

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() throws ConfigurationException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean(); 
        bean.setAnnotatedClasses(LogMessageEntity.class);
        bean.setHibernateProperties(getHibernateProperties());

        return bean;
    }

    private Properties getHibernateProperties() throws ConfigurationException {
        PropertiesConfiguration hibernateConfig = new PropertiesConfiguration("hibernate.properties");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateConfig.getProperty("hibernate.dialect"));

        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws ConfigurationException {
        return new HibernateTransactionManager(sessionFactoryBean().getObject());
    }

}
