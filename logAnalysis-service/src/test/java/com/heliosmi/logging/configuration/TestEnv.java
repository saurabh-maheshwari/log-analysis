package com.heliosmi.logging.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:testApp.properties")
@Profile("dev")
public class TestEnv {

}
