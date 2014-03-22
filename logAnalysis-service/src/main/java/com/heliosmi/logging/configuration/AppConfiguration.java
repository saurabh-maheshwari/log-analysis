package com.heliosmi.logging.configuration;

import javax.sql.DataSource;

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
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.heliosmi.logging.entity.LogMessageEntity;

@Configuration
@PropertySource("classpath:app.properties")
@ComponentScan("com.heliosmi.logging.aspect")
@EnableAspectJAutoProxy
//@EnableTransactionManagement
public class AppConfiguration {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Bean
    public HibernateTransactionManager transactionManager() throws ConfigurationException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactoryBean().getObject());
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
        log.info("System property: " + env.toString());
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
