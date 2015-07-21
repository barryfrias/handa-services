package handa.sms;

import handa.beans.dto.ClosePrompt;
import handa.beans.dto.PromptCount;
import handa.sms.mappers.PromptCountRowMapper;
import handa.sms.procs.ClosePromptProcedure;
import handa.sms.procs.GenericProcedure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

@Component
public class SmsDAOImpl
extends AbstractJdbcDAO
implements SmsDAO
{
    private GenericProcedure<PromptCount> getSosCountPerCityProcedure;
    private ClosePromptProcedure closePromptProcedure;

    @Autowired
    public SmsDAOImpl(JdbcTemplate jdbcTemplate,
                         @Value("${handa.command.sos.countPerCity.proc}") String getSosCountPerCityProcName,
                         @Value("${handa.command.close.prompt.proc}") String closePromptProcName)
    {
        super(jdbcTemplate);
        this.getSosCountPerCityProcedure = new GenericProcedure<>(dataSource(), getSosCountPerCityProcName, new PromptCountRowMapper());
        this.closePromptProcedure = new ClosePromptProcedure(dataSource(), closePromptProcName);
    }

    @Override
    public List<PromptCount> getSosCountPerCity()
    {
        return getSosCountPerCityProcedure.listValues();
    }

    @Override
    public int closePrompt(int id, ClosePrompt closePrompt)
    {
        return closePromptProcedure.closePrompt(id, closePrompt);
    }
}