package com.heliosmi.logging.configuration;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for all Integration tests. It will initialize the Spring
 * container.
 * 
 * @author Saurabh Maheshwari
 * 
 */
@ContextConfiguration(locations = { "classpath:/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseIntegration {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp() {
        // Not Needed right now.
    }

}
