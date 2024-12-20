package handa.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.SmsInbound;
import handa.procs.ReceiveSmsProcedure;

@Component
public class SmsDAOImpl
extends AbstractJdbcDAO
implements SmsDAO
{
    private ReceiveSmsProcedure receiveSmsProcedure;

    @Autowired
    public SmsDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.receiveSmsProcedure = new ReceiveSmsProcedure(dataSource());
    }

    @Override
    public String receive(SmsInbound smsInbound)
    {
        return receiveSmsProcedure.receive(smsInbound);
    }
}