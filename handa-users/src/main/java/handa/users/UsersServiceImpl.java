package handa.users;

import static com.google.common.base.Preconditions.checkNotNull;
import static handa.config.HandaUsersConstants.INVALID_CREDENTIALS;
import static handa.config.HandaUsersConstants.OK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

import frias.barry.LDAPController;
import handa.beans.dto.AppLog;
import handa.beans.dto.AuthInfo;
import handa.beans.dto.City;
import handa.beans.dto.Province;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.beans.dto.UserSearch;
import handa.config.HandaUsersConstants.PromptType;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;

@Component
public class UsersServiceImpl
implements UsersService
{
    final static Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private UsersDAO usersDAO;
    private LDAPController ldapController;
    private DBLoggerDAO dbLoggerDAO;
    private String uploadDirectory;

    @Autowired
    public UsersServiceImpl(UsersDAO usersDAO, LDAPController ldapController, DBLoggerDAO dbLoggerDAO, HandaProperties handaProperties)
    {
        this.usersDAO = usersDAO;
        this.ldapController = ldapController;
        this.dbLoggerDAO = dbLoggerDAO;
        this.uploadDirectory = handaProperties.get("handa.users.upload.directory");
    }

    @Override
    public String authByMobileNumber(AuthInfo authInfo)
    {
        String result = usersDAO.authByMobileNumber(authInfo);
        dbLoggerDAO.log(AppLog.client(null, authInfo.getMobileNumber(), "Tried to authenticate and result was %s", result));
        return result;
    }

    @Override
    public String authByMobileNumberAndUsername(AuthInfo authInfo)
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
                                              "Tried to authenticate thru ldap and result was %s", result));
                return result;
            default:
                dbLoggerDAO.log(AppLog.client(authInfo.getUsername(), authInfo.getMobileNumber(),
                                              "Tried to authenticate but not found in list of allowed users"));
                return result;
        }
    }

    @Override
    public String prompt(UserPrompt userPrompt, PromptType promptType)
    {
        String result = usersDAO.prompt(userPrompt, promptType);
        dbLoggerDAO.log(AppLog.client(null, userPrompt.getMobileNumber(),
                                      "Submitted prompt type %s and result was %s", promptType, result));
        return result;
    }

    @Override
    public Optional<UserInfo> getUserInfo(String mobileNumber)
    {
        return usersDAO.getUserInfo(mobileNumber);
    }

    @Override
    public String report(UserReport userReport)
    {
        String result = usersDAO.report(userReport);
        dbLoggerDAO.log(AppLog.client(null, userReport.getMobileNumber(),
                                      "Submitted report and result was %s", result));
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
}