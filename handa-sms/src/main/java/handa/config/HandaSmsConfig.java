package handa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import handa.sms.HandaSmsSender;

@ComponentScan(basePackages = { "handa.sms" })
@Configuration
@EnableScheduling
public class HandaSmsConfig
{
    @Autowired private HandaSmsSender handaSmsSender;

    @Scheduled(initialDelay=5000, fixedRateString="${handa.sms.sender.job.fixed.rate}")
    public void runOutboxProcessor()
    {
        handaSmsSender.processQueue();
    }
}