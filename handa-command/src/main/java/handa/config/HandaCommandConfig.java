package handa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = { "handa.command" })
@Configuration
public class HandaCommandConfig
{

}