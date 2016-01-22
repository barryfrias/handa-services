package handa.command;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.procs.CheckUsernameProcedure;
import handa.procs.ListRegistrationsProcedure;

@Component
public class CommandUsersDAOImpl
extends AbstractJdbcDAO
implements CommandUsersDAO
{
    private final CheckUsernameProcedure checkUsernameProcedure;
    private final ListRegistrationsProcedure listRegistrationsProcedure;

    @Autowired
    public CommandUsersDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.checkUsernameProcedure = new CheckUsernameProcedure(dataSource());
        this.listRegistrationsProcedure = new ListRegistrationsProcedure(dataSource());
    }

    @Override
    public String checkUsername(String username)
    {
        return checkUsernameProcedure.check(username);
    }

    @Override
    public List<Map<String, Object>> registrations(Long registrationId, String approvalStatus)
    {
        return listRegistrationsProcedure.list(registrationId, approvalStatus);
    }
}