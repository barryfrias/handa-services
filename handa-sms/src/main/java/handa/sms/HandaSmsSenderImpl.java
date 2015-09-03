package handa.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.beans.dto.AppLog;
import handa.beans.dto.SendSmsInput;
import handa.beans.dto.SendSmsOutput;
import handa.beans.dto.SmsOutboundQueue;
import handa.beans.dto.UpdateSmsOutboundQueue;
import handa.core.DBLoggerDAO;
import handa.sms.procs.GetSmsOutboundQueueProcedure;
import handa.sms.procs.UpdateSmsOutboundQueueProcedure;

@Component
public class HandaSmsSenderImpl
extends AbstractJdbcDAO
implements HandaSmsSender
{
    private GetSmsOutboundQueueProcedure getSmsOutboundQueueProcedure;
    private UpdateSmsOutboundQueueProcedure updateSmsOutboundQueueProcedure;
    private SmsService smsService;
    private DBLoggerDAO dbLogger;
    private String keyword;
    private String chargeId;
    private String queuePath;

    @Autowired
    public HandaSmsSenderImpl(JdbcTemplate jdbcTemplate,
                         SmsService smsService,
                         DBLoggerDAO dbLoggerDAO,
                         @Value("${handa.sms.keyword}") String keyword,
                         @Value("${handa.sms.charge.id}") String chargeId,
                         @Value("${handa.sms.queue.path}") String queuePath)
    {
        super(jdbcTemplate);
        this.smsService = smsService;
        this.dbLogger = dbLoggerDAO;
        this.keyword = keyword;
        this.chargeId = chargeId;
        this.queuePath = queuePath;
        this.getSmsOutboundQueueProcedure = new GetSmsOutboundQueueProcedure(dataSource());
        this.updateSmsOutboundQueueProcedure = new UpdateSmsOutboundQueueProcedure(dataSource());
    }

    //SLOW MODE
    @Override
    public void processQueue()
    {
        List<SmsOutboundQueue> list = getSmsOutboundQueueProcedure.list();
        if(list == null || list.isEmpty()) return;
        SendSmsInput wsInput = new SendSmsInput();
        wsInput.setKeyword(keyword);
        wsInput.setChargeId(chargeId);
        wsInput.setQueuePath(queuePath);
        UpdateSmsOutboundQueue update = new UpdateSmsOutboundQueue();
        int sent = 0, failed = 0;
        for(SmsOutboundQueue item : list)
        {
            wsInput.setMobile(item.getMobileNo());
            wsInput.setMessage(item.getMessage());
            Optional<SendSmsOutput> result = smsService.send(wsInput);
            if(result.isPresent())
            {
                update.setQueueId(item.getId());
                if("1".equals(result.get().getResult()))
                {
                    update.setUpdateStatus("processed");
                    sent++;
                }
                else
                {
                    update.setUpdateStatus("failed");
                    failed++;
                }
                update.setResponseMessage(result.get().toString());
                updateSmsOutboundQueueProcedure.update(update);
            }
        }
        dbLogger.log(AppLog.server("HandaSmsSender", String.format("Processed %s messages for sending. Sent=%s, Failed=%s", list.size(), sent, failed)));
    }
}