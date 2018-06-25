package handa.sms;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.AppLog;
import handa.beans.dto.SendSmsInput;
import handa.beans.dto.SendSmsOutput;
import handa.beans.dto.SmsOutboundQueue;
import handa.beans.dto.UpdateSmsOutboundQueue;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;
import handa.procs.GetSmsOutboundQueueProcedure;
import handa.procs.UpdateSmsOutboundQueueProcedure;

@Component
public class HandaSmsSenderImpl
extends AbstractJdbcDAO
implements HandaSmsSender
{
//    static Logger log = LoggerFactory.getLogger(HandaSmsSenderImpl.class);

    private GetSmsOutboundQueueProcedure getSmsOutboundQueueProcedure;
    private UpdateSmsOutboundQueueProcedure updateSmsOutboundQueueProcedure;
    private SmsService smsService;
    private DBLoggerDAO dbLogger;
    private String keyword;
    private String chargeId;
    private String queuePath;
    private volatile int sent = 0, failed = 0, ctr = 0;
    private boolean isProcessorRunning = false;

    @Autowired
    public HandaSmsSenderImpl(JdbcTemplate jdbcTemplate,
                              SmsService smsService,
                              DBLoggerDAO dbLoggerDAO,
                              HandaProperties handaProperties)
    {
        super(jdbcTemplate);
        this.smsService = smsService;
        this.dbLogger = dbLoggerDAO;
        this.keyword = handaProperties.get("handa.sms.keyword");
        this.chargeId = handaProperties.get("handa.sms.charge.id");
        this.queuePath = handaProperties.get("handa.sms.queue.path");
        this.getSmsOutboundQueueProcedure = new GetSmsOutboundQueueProcedure(dataSource());
        this.updateSmsOutboundQueueProcedure = new UpdateSmsOutboundQueueProcedure(dataSource());
    }

    @Override
    public synchronized void processQueue()
    {
        if(isProcessorRunning)
        {
//            log.info("SMS Outbox processor is currently running, aborting this turn...");
            return;
        }
        isProcessorRunning = true;
//        log.info("SMS Outbox queue processor is now running...");
        List<SmsOutboundQueue> list = getSmsOutboundQueueProcedure.list();
        if(list == null || list.isEmpty())
        {
//            log.info("Nothing to process...");
            isProcessorRunning = false;
            return;
        }
        Executor executor = Executors.newFixedThreadPool(10);
        final Object lock = new Object();
        for(final SmsOutboundQueue item : list)
        {
            executor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    SendSmsInput wsInput = new SendSmsInput();
                    wsInput.setKeyword(keyword);
                    wsInput.setChargeId(chargeId);
                    wsInput.setQueuePath(queuePath);
                    wsInput.setMobile(item.getMobileNo());
                    wsInput.setMessage(item.getMessage());
                    UpdateSmsOutboundQueue update = new UpdateSmsOutboundQueue();
                    update.setQueueId(item.getId());
                    Optional<SendSmsOutput> result = smsService.send(wsInput);
//                    SendSmsOutput output = new SendSmsOutput();
//                    output.setResult("1");
//                    Optional<SendSmsOutput> result = Optional.of(output);
//                    try
//                    {
//                        Thread.sleep(1000L);
//                    }
//                    catch(InterruptedException e)
//                    {
//                        e.printStackTrace();
//                    }
                    if(result.isPresent())
                    {
                        if("1".equals(result.get().getResult()))
                        {
                            update.setUpdateStatus("processed");
                            synchronized(lock)
                            {
                                sent++;
                            }
                        }
                        else
                        {
                            update.setUpdateStatus("failed");
                            synchronized(lock)
                            {
                                failed++;
                            }
                        }
                        update.setResponseMessage(result.get().toString());
                    }
                    else
                    {
                        update.setUpdateStatus("failed");
                        update.setResponseMessage("empty response");
                        failed++;
                    }
                    updateSmsOutboundQueueProcedure.update(update);
                    synchronized(lock)
                    {
                        ctr++;
                    }
                }
            });

            try
            {
                Thread.sleep(10L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        while(ctr < list.size())
        {
            //wait until all list items have been processed
        }

        dbLogger.log(AppLog.server("HandaSmsSender", "Processed %s messages for sending. Sent=%s, Failed=%s", list.size(), sent, failed));
        sent = 0; failed = 0; ctr = 0;
        isProcessorRunning = false;
    }
}