package com.heliosmi.logging.configuration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ActiveMQConfigurationTest extends BaseIntegration {

    @Autowired
    private ActiveMQConfiguration activeMQConfiguration;

    @Test
    public void testConfig() {
        assertNotNull(activeMQConfiguration);
    }

}
