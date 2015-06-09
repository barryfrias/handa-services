package handa;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = {
        "handa.users"
})
@ImportResource({
    "classpath*:handa-test-properties-configuration.xml"
})
public class HandaUsersTestConfiguration
{
    @Value("${jdbc.driver.class.name:oracle.jdbc.OracleDriver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public Client jerseyClient()
    {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(HttpAuthenticationFeature.basicBuilder().build());
        return ClientBuilder.newClient(clientConfig);
    }

    @Bean(destroyMethod = "close")
    DataSource dataSource()
    {
        PoolProperties p = new PoolProperties();
        p.setUrl(url);
        p.setDriverClassName(driverClassName);
        p.setUsername(username);
        p.setPassword(password);
        p.setTestWhileIdle(true);
        p.setValidationQuery("SELECT 1 FROM DUAL");
        p.setRemoveAbandoned(true);

        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(p);
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }
}