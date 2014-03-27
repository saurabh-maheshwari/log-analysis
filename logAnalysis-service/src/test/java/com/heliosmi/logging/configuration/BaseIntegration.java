package com.heliosmi.logging.configuration;

import org.junit.Before;
import org.junit.runner.RunWith;
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

    @Before
    public void setUp() {
        // Not Needed right now.
    }

}
