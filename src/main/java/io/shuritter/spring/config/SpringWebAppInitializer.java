package io.shuritter.spring.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Describes the classes, resources, and configuration of the application,
 * and how the server will use them to execute web requests
 * @author Alexander Nyrkov
 */
public class SpringWebAppInitializer implements WebApplicationInitializer {

    /**
     * Configure the given ServletContext with any servlets, filters, listeners context-params and attributes
     * necessary for initializing this web application
     * @param servletContext defines a set of methods that a servlet uses to communicate with its servlet container
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
                new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }

    /**
     * @return configuration which accepts annotated classes as input
     */
    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("io.shuritter.spring.config");
        return context;
    }
}