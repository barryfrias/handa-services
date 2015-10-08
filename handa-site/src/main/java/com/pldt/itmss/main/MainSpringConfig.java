package com.pldt.itmss.main;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

import frias.barry.LDAPController;
import handa.core.DBLoggerDAO;
import handa.core.DBLoggerDAOImpl;
import handa.core.HandaProperties;

@ImportResource({ "classpath*:handa-properties-configuration.xml" })
@ComponentScan(basePackages = { "handa.config" }) // contains the class that has the @Configuration annotation for each modules
@Configuration
public class MainSpringConfig
{
    @PostConstruct
    private void postConstruct()
    {
        // redirects logging made java.util.logging
        // which was used by jersey to slf4j
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    @Bean
    public Client jerseyClient()
    {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(HttpAuthenticationFeature.basicBuilder().build());
        return ClientBuilder.newClient(clientConfig);
    }

    @Bean(destroyMethod = "close")
    DataSource dataSource(@Value("${jdbc.url}") final String url,
                          @Value("${jdbc.username}") final String username,
                          @Value("${jdbc.password}") final String password,
                          @Value("${jdbc.driver.class.name:oracle.jdbc.OracleDriver}") final String driverClassName,
                          @Value("${jdbc.pool.fair.queue}") final boolean isFairQueue,
                          @Value("${jdbc.pool.max.active}") final int maxActive,
                          @Value("${jdbc.pool.initial.size}") final int initialSize,
                          @Value("${jdbc.pool.max.age}") final int maxAge,
                          @Value("${jdbc.pool.max.idle}") final int maxIdle,
                          @Value("${jdbc.pool.min.idle}") final int minIdle,
                          @Value("${jdbc.pool.time.between.eviction.runs.millis}") final int timeBetweenEvictionRunsMillis,
                          @Value("${jdbc.pool.min.evictable.idle.time.millis}") final int minEvictableIdleTimeMillis)
    {
        @SuppressWarnings("serial")
        final PoolProperties p = new PoolProperties()
        {{
            setUrl(url);
            setDriverClassName(driverClassName);
            setUsername(username);
            setPassword(password);
            setTestWhileIdle(true);
            setValidationQuery("SELECT 1 FROM DUAL");
            setFairQueue(isFairQueue);
            setMaxActive(maxActive);
            setInitialSize(initialSize);
            setMaxAge(maxAge);
            setMaxIdle(maxIdle);
            setMinIdle(minIdle);
            setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            setRemoveAbandoned(true);
            setJmxEnabled(false);
        }};
        DataSource dataSource = new DataSource()
        {{
            setPoolProperties(p);
            setDefaultAutoCommit(false);
        }};
        return dataSource;
    }

    @Bean
    HandaProperties handaProperties(DataSource dataSource)
    {
        return new HandaProperties(dataSource);
    }

    @Bean
    LDAPController ldapAuthenticatorImpl(HandaProperties properties)
    {
        LDAPController controller = new LDAPController();
        controller.setLdapServers(properties.getArray("ldap.servers"));
        controller.setDomainNames(properties.getArray("ldap.domains"));
        return controller;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }

    @Bean
    DBLoggerDAO dbLoggerDAO(JdbcTemplate jdbcTemplate)
    {
        return new DBLoggerDAOImpl(jdbcTemplate);
    }
}