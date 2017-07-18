package com.bmps.logistica.abastecimentodoca.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private void initH2Console(ServletContext servletContext) {
        log.debug("Initialize H2 console");
        ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("H2Console", new org.h2.server.web.WebServlet());
        h2ConsoleServlet.addMapping("/h2-console/*");
        h2ConsoleServlet.setInitParameter("-properties", "src/main/resources/");
        h2ConsoleServlet.setLoadOnStartup(1);
    }
}
