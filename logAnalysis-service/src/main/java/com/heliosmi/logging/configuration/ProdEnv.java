package com.heliosmi.logging.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:app.properties")
@Profile("default")
public class ProdEnv implements EnvConfig{
    @Autowired
    private Environment environment;
    
    
    @Override
    public String getProperty(String key){
        return environment.getProperty(key);
    }

}
