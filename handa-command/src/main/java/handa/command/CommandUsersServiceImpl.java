package handa.command;

import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaCommandConstants.OK;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import frias.barry.LDAPController;
import handa.beans.dto.AppLog;
import handa.beans.dto.UserLogin;
import handa.core.DBLoggerDAO;

/**
 * @author bfrias
 */
@Component
public class CommandUsersServiceImpl implements CommandUsersService
{
    private LDAPController ldapController;
    private CommandUsersDAO commandUsersDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public CommandUsersServiceImpl(LDAPController ldapController, CommandUsersDAO commandUsersDAO, DBLoggerDAO dbLoggerDAO)
    {
        this.ldapController = ldapController;
        this.commandUsersDAO = commandUsersDAO;
        this.dbLoggerDAO = dbLoggerDAO;
    }

    @Override
    public boolean authenticate(UserLogin userLogin)
    {
        checkNotNull(userLogin, "userLogin object can't be null");
        checkNotNull(userLogin.getUsername(), "userLogin.username can't be null");
        checkNotNull(userLogin.getPassword(), "userLogin.password can't be null");
        String result = commandUsersDAO.checkUsername(userLogin.getUsername());
        switch(result)
        {
                case OK:
                    boolean isOk = ldapController.login(userLogin.getUsername().toLowerCase(), userLogin.getPassword());
                    dbLoggerDAO.log(AppLog.server(userLogin.getUsername(), "Tried to login thru ldap and result was %s", isOk));
                    return isOk;
                default:
                    dbLoggerDAO.log(AppLog.server(userLogin.getUsername(), "Tried to login but not found in list of allowed users."));
                    return false;
        }
    }

    @Override
    public List<Map<String, Object>> registrations(String approvalStatus)
    {
        return commandUsersDAO.registrations(null, approvalStatus);
    }
}