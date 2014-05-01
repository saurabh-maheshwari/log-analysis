package com.heliosmi.logging.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initializer class to be used in place of web.xml.
 * 
 * @author Saurabh Maheshwari
 * 
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        Class[] rootConfigs = new Class[] { AppConfig.class, ProdEnv.class };
        return rootConfigs;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        Class[] servletConfigs = new Class[] { WebMvcConfig.class };
        return servletConfigs;
    }

    @Override
    protected String[] getServletMappings() {
        String[] mappings = new String[] { "/" };
        return mappings;
    }

}
