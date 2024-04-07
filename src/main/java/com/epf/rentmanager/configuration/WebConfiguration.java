package com.epf.rentmanager.configuration;

import org.springframework.context.annotation.*;
import org.springframework.web.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.*;
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}

