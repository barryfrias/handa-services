package handa.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.command.procs.CheckUsernameProcedure;

@Component
public class CommandUsersDAOImpl
extends AbstractJdbcDAO
implements CommandUsersDAO
{
    private CheckUsernameProcedure checkUsernameProcedure;

    @Autowired
    public CommandUsersDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.checkUsernameProcedure = new CheckUsernameProcedure(dataSource());
    }

    @Override
    public String checkUsername(String username)
    {
        return checkUsernameProcedure.check(username);
    }
}