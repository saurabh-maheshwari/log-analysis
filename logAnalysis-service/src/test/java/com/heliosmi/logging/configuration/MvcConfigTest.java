package com.heliosmi.logging.configuration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = { AppConfig.class, MVCConfig.class, TestEnv.class, ProdEnv.class })
@ContextConfiguration(classes = {  MVCConfig.class })
@ActiveProfiles("dev")
public class MvcConfigTest {

    @Test
    public void test() {

    }

}
