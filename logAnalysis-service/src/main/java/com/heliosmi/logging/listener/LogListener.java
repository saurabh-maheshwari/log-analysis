package com.heliosmi.logging.listener;

import java.lang.reflect.InvocationTargetException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heliosmi.logging.entity.LogMessageEntity;

/**
 * Listener class to pull message from ActiveMQ queue. It will populate the
 * LogMessageEntity from ActiveMqMapMessage using BeanUtils.
 * 
 * @author Saurabh Maheshwari
 * 
 */
@Component
public class LogListener implements MessageListener {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional("transactionManager")
    public void onMessage(Message message) {
        if (message instanceof ActiveMQMapMessage) {
            Session session = sessionFactory.getCurrentSession();
            ActiveMQMapMessage activeMQMapMessage = (ActiveMQMapMessage) message;
            LogMessageEntity logMessageEntity = new LogMessageEntity();
            try {
                BeanUtils.populate(logMessageEntity, activeMQMapMessage.getContentMap());
            } catch (IllegalAccessException | InvocationTargetException | JMSException ex) {
                log.error("Cannot transform MapMessage to LogMessageEntity" + ExceptionUtils.getRootCauseMessage(ex));
            }
            session.save(logMessageEntity);
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory=sessionFactory;        
    }

}
