package handa.command;

import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaCommandConstants.OK;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import frias.barry.LDAPController;
import handa.beans.dto.AppLog;
import handa.beans.dto.RegistrationAction;
import handa.beans.dto.RegistrationActionResult;
import handa.beans.dto.UserLogin;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;
import handa.core.MailerRestClient;
import handa.core.TexterRestClient;

/**
 * @author bfrias
 */
@Component
public class CommandUsersServiceImpl implements CommandUsersService
{
    private static final String CODE = "{code}";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final LDAPController ldapController;
    private final CommandUsersDAO commandUsersDAO;
    private final DBLoggerDAO dbLoggerDAO;
    private final TexterRestClient texterRestClient;
    private final MailerRestClient mailerRestClient;
    private final String smsActivationMsg;
    private final String mailActivationMsg;
    private final String mailFrom;
    private final String mailSubject;

    @Autowired
    public CommandUsersServiceImpl(LDAPController ldapController,
                                   CommandUsersDAO commandUsersDAO,
                                   DBLoggerDAO dbLoggerDAO,
                                   TexterRestClient texterRestClient,
                                   MailerRestClient mailerRestClient,
                                   HandaProperties props)
    {
        this.ldapController = ldapController;
        this.commandUsersDAO = commandUsersDAO;
        this.dbLoggerDAO = dbLoggerDAO;
        this.texterRestClient = texterRestClient;
        this.mailerRestClient = mailerRestClient;
        this.smsActivationMsg = checkNotNull(props.get("handa.sms2.activation.message"));
        this.mailActivationMsg = checkNotNull(props.get("handa.mail.activation.message"));
        this.mailFrom = checkNotNull(props.get("handa.mail.otp.from"));
        this.mailSubject = checkNotNull(props.get("handa.mail.otp.subject"));
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

    @Override
    public List<Map<String, Object>> registrationsById(long registrationId)
    {
        return commandUsersDAO.registrations(registrationId, null);
    }

    @Override
    public RegistrationActionResult registrationsAction(long registrationId, RegistrationAction action)
    {
        checkNotNull(action, "registrationAction object should not be null");
        checkNotNull(action.getUsername(), "username should not be null");
        checkNotNull(action.getAction(), "action should not be null");
        RegistrationActionResult result = commandUsersDAO.registrationsAction(registrationId, action);
        dbLoggerDAO.log(AppLog.server(action.getUsername(),
                                      "Registration action [%s] on id [%s] and result was %s",
                                      action.getAction(), registrationId, result));
        if(result.getPasscode() != null)
        {
            sendActivationMailAndSms(result.getPasscode(), result.getMail(), result.getMobileNumber());
        }
        return result;
    }

    private void sendActivationMailAndSms(String passcode, String email, String mobileNumber)
    {
        if(passcode != null)
        {
            if(null != email && email.matches(EMAIL_PATTERN))
            {
                String message = mailActivationMsg.replace(CODE, passcode);
                mailerRestClient.sendMail(this.mailFrom, email, this.mailSubject, message);
                dbLoggerDAO.log(AppLog.server("CommandUsersService", "Activation passcode sent via mail to %s", email));
            }
            if(mobileNumber != null)
            {
                String message = smsActivationMsg.replace(CODE, passcode);
                texterRestClient.sendSms(mobileNumber, message);
                dbLoggerDAO.log(AppLog.server("CommandUsersService", "Activation passcode sent via sms to %s", mobileNumber));
            }
        }
    }
}