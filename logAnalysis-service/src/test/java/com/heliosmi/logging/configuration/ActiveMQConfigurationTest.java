package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class ActiveMQConfigurationTest extends BaseIntegration {

    @Autowired
    private ActiveMQConfiguration activeMQConfiguration;
    
    @Autowired
    private DefaultMessageListenerContainer defaultMessageListenerContainer;

    @Test
    public void testConfig() {
        assertNotNull(activeMQConfiguration);
        assertNotNull(defaultMessageListenerContainer);
    }

}
