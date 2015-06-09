package handa.command;

import handa.command.procs.CheckUsernameProcedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

@Component
public class CommandUsersDAOImpl
extends AbstractJdbcDAO
implements CommandUsersDAO
{
    private CheckUsernameProcedure checkUsernameProcedure;

    @Autowired
    public CommandUsersDAOImpl(JdbcTemplate jdbcTemplate,
                         @Value("${handa.command.users.check.username.proc}") String checkUsernameProcName)
    {
        super(jdbcTemplate);
        this.checkUsernameProcedure = new CheckUsernameProcedure(dataSource(), checkUsernameProcName);
    }

    @Override
    public String checkUsername(String username)
    {
        return checkUsernameProcedure.check(username);
    }
}