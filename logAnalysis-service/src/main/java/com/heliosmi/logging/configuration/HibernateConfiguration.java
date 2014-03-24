package com.heliosmi.logging.configuration;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@EnableTransactionManagement
public class HibernateConfiguration {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

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
