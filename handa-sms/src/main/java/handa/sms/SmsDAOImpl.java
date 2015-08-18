package handa.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.beans.dto.SmsInbound;
import handa.sms.procs.ReceiveSmsProcedure;

@Component
public class SmsDAOImpl
extends AbstractJdbcDAO
implements SmsDAO
{
    private ReceiveSmsProcedure receiveSmsProcedure;

    @Autowired
    public SmsDAOImpl(JdbcTemplate jdbcTemplate,
                      @Value("${sms.receive.proc}") String smsReceiveProcName)
    {
        super(jdbcTemplate);
        this.receiveSmsProcedure = new ReceiveSmsProcedure(dataSource(), smsReceiveProcName);
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        return receiveSmsProcedure.receive(smsInbound);
    }
}