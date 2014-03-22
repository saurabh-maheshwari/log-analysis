package com.heliosmi.logging.listener;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LogListenerTest {
    
    @Autowired
    private LogListener logListener;

    @Test
    public void testconfig() {
        assertNotNull(logListener);
    }

}
