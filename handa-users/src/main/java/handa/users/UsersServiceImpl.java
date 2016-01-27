package handa.users;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaUsersConstants.INVALID_CREDENTIALS;
import static handa.config.HandaUsersConstants.OK;
import static handa.config.HandaUsersConstants.USER_NOT_FOUND;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import frias.barry.LDAPController;
import handa.beans.dto.AppLog;
import handa.beans.dto.AuthInfo;
import handa.beans.dto.City;
import handa.beans.dto.Company;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.LdapUser;
import handa.beans.dto.LdapUserSearch;
import handa.beans.dto.Province;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserRegistration;
import handa.beans.dto.UserReport;
import handa.beans.dto.UserSearch;
import handa.beans.dto.UserVerificationResult;
import handa.config.HandaUsersConstants.PromptType;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;
import handa.core.TexterRestClient;

@Component
public class UsersServiceImpl
implements UsersService
{
    private static final String CODE = "{code}";
    final static Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private UsersDAO usersDAO;
    private LDAPController ldapController;
    private LdapSearchUserRestClient ldapSearchUserRestClient;
    private final TexterRestClient texterRestClient;
    private DBLoggerDAO dbLoggerDAO;
    private String uploadDirectory;
    private String passcodeSentSpiel;
    private String smsPasscodeMsg;

    @Autowired
    public UsersServiceImpl(UsersDAO usersDAO,
                            LDAPController ldapController,
                            DBLoggerDAO dbLoggerDAO,
                            TexterRestClient texterRestClient,
                            HandaProperties props,
                            Client jerseyClient)
    {
        this.usersDAO = usersDAO;
        this.ldapController = ldapController;
        this.dbLoggerDAO = dbLoggerDAO;
        this.uploadDirectory = props.get("handa.users.upload.directory");
        this.ldapSearchUserRestClient = new LdapSearchUserRestClient(jerseyClient, props.get("ldap.search.user.ws.url"));
        this.texterRestClient = texterRestClient;
        this.passcodeSentSpiel = checkNotNull(props.get("handa.passcode.sent.spiel"));
        this.smsPasscodeMsg = checkNotNull(props.get("handa.sms2.passcode.message"));
    }

    @Override
    public String authByMobileNumber(AuthInfo authInfo, DeviceInfo deviceInfo)
    {
        String result = usersDAO.authByMobileNumber(authInfo);
        dbLoggerDAO.log(AppLog.client(null, authInfo.getMobileNumber(), "Tried to authenticate and result was %s [%s]", result, deviceInfo));
        return result;
    }

    @Override
    public String authByMobileNumberAndUsername(AuthInfo authInfo, DeviceInfo deviceInfo)
    {
        checkNotNull(authInfo, "authInfo object can't be null");
        checkNotNull(authInfo.getUsername(), "authInfo.username object can't be null");
        checkNotNull(authInfo.getPassword(), "authInfo.password object can't be null");
        String result = usersDAO.authByMobileNumberAndUsername(authInfo);
        switch(result)
        {
            case OK:
                boolean authenticated = ldapController.login(authInfo.getUsername().toLowerCase(), authInfo.getPassword());
                if(!authenticated)
                {
                    result = INVALID_CREDENTIALS ;
                }
                dbLoggerDAO.log(AppLog.client(authInfo.getUsername(), authInfo.getMobileNumber(),
                                              "Tried to authenticate thru ldap and result was %s [%s]", result, deviceInfo));
                return result;
            default:
                dbLoggerDAO.log(AppLog.client(authInfo.getUsername(), authInfo.getMobileNumber(),
                                              "Tried to authenticate but not found in list of allowed users  [%s]", deviceInfo));
                return result;
        }
    }

    @Override
    public String loginByPasscode(AuthInfo authInfo, DeviceInfo deviceInfo)
    {
        String result = usersDAO.loginByPasscode(authInfo);
        dbLoggerDAO.log(AppLog.client(null, authInfo.getMobileNumber(),
                                      "Tried to authenticate thru passcode and result was %s [%s]", result, deviceInfo));
        return result;
    }

    @Override
    public String prompt(UserPrompt userPrompt, PromptType promptType, DeviceInfo deviceInfo)
    {
        String result = usersDAO.prompt(userPrompt, promptType);
        dbLoggerDAO.log(AppLog.client(null, userPrompt.getMobileNumber(),
                                      "Submitted prompt type %s and result was %s [%s]", promptType, result, deviceInfo));
        return result;
    }

    @Override
    public Optional<UserInfo> getUserInfo(String mobileNumber)
    {
        return usersDAO.getUserInfo(mobileNumber);
    }

    @Override
    public String report(DeviceInfo deviceInfo, UserReport userReport)
    {
        String result = usersDAO.report(deviceInfo, userReport);
        dbLoggerDAO.log(AppLog.client(null, userReport.getMobileNumber(),
                                      "Submitted report and result was %s [%s]", result, deviceInfo));
        return result;
    }

    @Override
    public String uploadFile(InputStream uploadedInputStream, String filename)
    {
        File directory = new File(uploadDirectory);
        File finalFile = new File(directory, checkNotNull(filename, "filename can't be null"));
        if(finalFile.isFile())
        {
            finalFile.delete();
        }
        File tempFile = new File(directory, filename + ".tmp");
        try(OutputStream out = new FileOutputStream(tempFile))
        {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            uploadedInputStream.close();
        }
        catch (IOException e)
        {
            log.error("IO Exception while processing uploaded file", e);
            return "IO ERROR OCCURED";
        }
        tempFile.renameTo(finalFile);
        return OK;
    }

    @Override
    public List<UserInfo> getUsers()
    {
        return usersDAO.getUsers();
    }

    @Override
    public String checkAppVersion(String versionString)
    {
        return usersDAO.checkAppVersion(versionString);
    }

    @Override
    public List<UserInfo> searchByName(UserSearch userSearch)
    {
        return usersDAO.searchByName(userSearch);
    }

    @Override
    public List<City> getCitiesLov()
    {
        return usersDAO.getCitiesLov();
    }

    @Override
    public List<Province> getProvincesLov()
    {
        return usersDAO.getProvincesLov();
    }

    @Override
    public List<Company> getCompaniesLov()
    {
        return usersDAO.getCompaniesLov();
    }

    @Override
    public String addUser(User user)
    {
        String result = usersDAO.addUser(user);
        dbLoggerDAO.log(AppLog.server(user.getModifiedBy(), "Tried to add user %s, result was %s", user.getAdUsername(), result));
        return result;
    }

    @Override
    public String editUser(User user)
    {
        String result = usersDAO.editUser(user);
        dbLoggerDAO.log(AppLog.server(user.getModifiedBy(), "Tried to modify user %s, result was %s", user.getAdUsername(), result));
        return result;
    }

    @Override
    public Optional<LdapUser> ldapSearchUser(LdapUserSearch userSearch)
    {
        return ldapSearchUserRestClient.search(userSearch);
    }

    @Override
    public String register(UserRegistration registration, DeviceInfo deviceInfo)
    {
        String result = usersDAO.register(registration);
        dbLoggerDAO.log(AppLog.client(null, registration.getMobileNumber(),
                                     "Registration activity. Input: %s, Result was %s [%s]", registration, result , deviceInfo));
        return result;
    }

    @Override
    public String registerDomainUser(UserRegistration userRegistration, DeviceInfo deviceInfo)
    {
        checkNotNull(userRegistration, "userRegistration object should note be null.");
        checkNotNull(userRegistration.getUsername(), "username should note be null.");
        checkNotNull(userRegistration.getPassword(), "password should note be null.");
        checkNotNull(userRegistration.getCompanyCode(), "companyCode should note be null.");
        checkNotNull(userRegistration.getMobileNumber(), "mobileNumber should note be null.");
        checkNotNull(userRegistration.getFirstName(), "firstName should not be null");
        checkNotNull(userRegistration.getLastName(), "lastName should not be null");
        LdapUserSearch userSearch = new LdapUserSearch();
        String username = userRegistration.getUsername().split("@")[0];
        userSearch.setUsername(username);
        userSearch.setDomain(userRegistration.getCompanyCode());
        Optional<LdapUser> ldapUser = absent();
        String ldapSearchResultMessage;
        try
        {
            ldapUser = this.ldapSearchUser(userSearch);
            ldapSearchResultMessage = ldapUser.isPresent()? "User found." : USER_NOT_FOUND;
        }
        catch(RuntimeException e)
        {
            if(!e.getMessage().contains("Invalid domain"))
            {
                throw e;
            }
            ldapSearchResultMessage = e.getMessage();
        }
        dbLoggerDAO.log(AppLog.client(username, userRegistration.getMobileNumber(),
                                     "Registration activity. Ldap search and result was \"%s\" [%s]",
                                     ldapSearchResultMessage, deviceInfo));
        if(ldapUser.isPresent())
        {
            boolean authenticated = ldapController.login(username.toLowerCase(), userRegistration.getPassword());
            dbLoggerDAO.log(AppLog.client(username, userRegistration.getMobileNumber(),
                                          "Registration activity. Tried to authenticate thru %s ldap and result was %s [%s]",
                                          userRegistration.getCompanyCode(), authenticated, deviceInfo));
            if(authenticated)
            {
                aggregate(userRegistration, ldapUser.get());
                String result = usersDAO.registerDomainUser(userRegistration);
                dbLoggerDAO.log(AppLog.client(null, userRegistration.getMobileNumber(),
                                "Registration activity. Input: %s, Result was %s [%s]", userRegistration, result , deviceInfo));
                return result;
            }
            return INVALID_CREDENTIALS;
        }
        return USER_NOT_FOUND;
    }

    @Override
    public UserVerificationResult verify(AuthInfo authInfo, DeviceInfo deviceInfo)
    {
        UserVerificationResult result = usersDAO.verify(authInfo);
        dbLoggerDAO.log(AppLog.client("N/A", authInfo.getMobileNumber(), "User verification activity. Result was [method: %s] [%s]", result.getAuthMethod(), deviceInfo));
        if("passcode".equals(result.getAuthMethod()) && result.getPasscode() != null)
        {
            result.setMessage(passcodeSentSpiel);
            sendPasscodeViaSms(result.getPasscode(), result.getMobileNumber());
        }
        return result;
    }

    private void sendPasscodeViaSms(String passcode, String mobileNumber)
    {
        if(passcode != null)
        {
            if(mobileNumber != null)
            {
                String message = smsPasscodeMsg.replace(CODE, passcode);
                texterRestClient.sendSms(mobileNumber, message);
                dbLoggerDAO.log(AppLog.server(this.getClass().getSimpleName(), "Passcode sent via sms to %s", mobileNumber));
            }
        }
    }

    private void aggregate(UserRegistration ur, LdapUser lu)
    {
        ur.setUsername(lu.getAdUsername());
        ur.setDepartment(lu.getDepartment());
        ur.setEmployeeNumber(lu.getEmployeeNumber());
        ur.setFirstName(MoreObjects.firstNonNull(lu.getFirstName(), ur.getFirstName()));
        ur.setImmediateHead(lu.getImmediateHead());
        ur.setLastName(MoreObjects.firstNonNull(lu.getLastName(), ur.getLastName()));
        ur.setMiddleName(lu.getMiddleName());
        ur.setPosition(lu.getPosition());
    }

}