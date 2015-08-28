package handa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import handa.command.CommandSmsProcessor;

@ComponentScan(basePackages = { "handa.command" })
@Configuration
@EnableScheduling
public class HandaCommandConfig
{
    @Autowired private CommandSmsProcessor commandSmsProcessor;

    @Scheduled(initialDelay=10000, fixedRateString="${handa.command.process.sms.outbox.job.fixed.rate}")
    public void runOutboxProcessor()
    {
        commandSmsProcessor.processOutbox();
    }
}