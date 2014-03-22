package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.heliosmi.logging.entity.LogMessageEntity;

@ContextConfiguration(locations={"classpath:/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AppConfigurationTest {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /*@Autowired
    private HibernateTransactionManager transactionManager;
*/
    @Test
    public void testConfig() {
        assertNotNull(sessionFactory);
        //assertNotNull(transactionManager);
    }
    
    @Test
    @Transactional
    public void saveLogMessageEntity(){
        LogMessageEntity logMessageEntity = new LogMessageEntity();
        logMessageEntity.setApplicationName("saurabhMaheshwari");
        
        Session session = sessionFactory.getCurrentSession();
        session.save(logMessageEntity);
    }

}
