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
 * Configuration class for Hibernate beans. <code>transactionManager</code> is
 * initialized here.
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

    /**
     * Defines a <code>transactionManager</code> for Hibernate managed entities.
     * 
     * @return
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactoryBean()
                .getObject());
        return transactionManager;
    }

    /**
     * Defines a <code>LocalSessionFactoryBean</code>. It will scan entities
     * underneath scanned package.
     * 
     * @return LocalSessionFactoryBean
     */
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("com.heliosmi.logging.entity");

        return bean;
    }

    /**
     * Defines local Data Source. Connection properties are pulled out of
     * <code>environment</code> bean.
     * 
     * @return
     */
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
