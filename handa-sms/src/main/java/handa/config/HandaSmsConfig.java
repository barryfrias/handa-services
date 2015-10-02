package handa.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import handa.core.HandaProperties;
import handa.sms.HandaSmsSender;

@ComponentScan(basePackages = { "handa.sms" })
@Configuration
@EnableScheduling
public class HandaSmsConfig
{
    @Autowired private HandaSmsSender handaSmsSender;
    @Autowired private HandaProperties handaProperties;
    private boolean isProcessorEnabled;

    public HandaSmsConfig() {}

    @PostConstruct
    private void postConstruct()
    {
        String val = handaProperties.get("handa.sms.queue.processor.enabled");
        this.isProcessorEnabled = Boolean.valueOf(val);
    }

    @Scheduled(initialDelay=5000, fixedRateString="${handa.sms.sender.job.fixed.rate}")
    public void runOutboxProcessor()
    {
        if(isProcessorEnabled)
        {
            handaSmsSender.processQueue();
        }
    }
}