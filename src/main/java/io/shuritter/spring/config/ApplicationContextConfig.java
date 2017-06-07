package io.shuritter.spring.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class
 * @author Alexander Nyrkov
 */
@Configuration
@EnableWebMvc
@EnableWebSecurity
@ComponentScan("io.shuritter.spring")
@EnableTransactionManagement
public class ApplicationContextConfig extends WebSecurityConfigurerAdapter {

    /**
     * @return basic configured via JavaBeans properties
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/shuritter");

        return dataSource;
    }

    /**
     * Create a SessionFactory using the properties and mappings in this configuration
     * @param dataSource basic configured via JavaBeans properties
     * @return the build SessionFactory
     */
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("io.shuritter.spring");

        return sessionBuilder.buildSessionFactory();
    }

    /**
     * @param sessionFactory to manage transactions
     * @return new HibernateTransactionManager instance
     */
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}