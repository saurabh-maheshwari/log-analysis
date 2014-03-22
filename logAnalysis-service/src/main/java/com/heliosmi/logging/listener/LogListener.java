package com.heliosmi.logging.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component
public class LogListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub

    }

}
