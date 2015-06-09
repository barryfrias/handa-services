package handa.users;

import java.util.List;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.config.HandaUsersConstants.PromptType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itmss.core.utils.AbstractJdbcDAO;

@Component
public class UsersDAOImpl
extends AbstractJdbcDAO
implements UsersDAO
{
    private final AuthByMobileProcedure authByMobileProcedure;
    private final AuthByMobileAndUsernameProcedure authByMobileAndUsernameProcedure;
    private final UserPromptProcedure userPromptProcedure;
    private final UserInfoProcedure userInfoProcedure;
    private final UserReportProcedure userReportProcedure;
    private final CheckMobileAppVersionProcedure checkMobileAppVersionProcedure;

    @Autowired
    public UsersDAOImpl(JdbcTemplate jdbcTemplate,
                        @Value("${handa.users.auth.proc}") String authByMobileProcName,
                        @Value("${handa.users.auth2.proc}") String authByMobileAndUsernameProcName,
                        @Value("${handa.users.prompt.proc}") String userPromptProcName,
                        @Value("${handa.users.info.proc}") String userInfoProcName,
                        @Value("${handa.users.report.proc}") String userReportProcName,
                        @Value("${handa.users.check.app.version.proc}") String checkMobileAppVersionProcName)
    {
        super(jdbcTemplate);
        this.authByMobileProcedure = new AuthByMobileProcedure(dataSource(), authByMobileProcName);
        this.authByMobileAndUsernameProcedure = new AuthByMobileAndUsernameProcedure(dataSource(), authByMobileAndUsernameProcName);
        this.userPromptProcedure = new UserPromptProcedure(dataSource(), userPromptProcName);
        this.userInfoProcedure = new UserInfoProcedure(dataSource(), userInfoProcName);
        this.userReportProcedure = new UserReportProcedure(dataSource(), userReportProcName);
        this.checkMobileAppVersionProcedure = new CheckMobileAppVersionProcedure(dataSource(), checkMobileAppVersionProcName);
    }

    @Override
    public String authByMobileNumber(AuthInfo authInfo)
    {
        return authByMobileProcedure.authenticate(authInfo);
    }

    @Override
    public String authByMobileNumberAndUsername(AuthInfo authInfo)
    {
        return authByMobileAndUsernameProcedure.authenticate(authInfo);
    }

    @Override
    public String prompt(UserPrompt userPrompt, PromptType promptType)
    {
        return userPromptProcedure.prompt(userPrompt, promptType);
    }

    @Override
    public Optional<UserInfo> getuserInfo(String mobileNumber)
    {
        return userInfoProcedure.getInfo(mobileNumber);
    }

    @Override
    public String report(UserReport userReport)
    {
        return userReportProcedure.report(userReport);
    }

    @Override
    public List<UserInfo> getUsers()
    {
        return userInfoProcedure.list();
    }

    @Override
    public String checkAppVersion(String versionString)
    {
        return checkMobileAppVersionProcedure.check(versionString);
    }
}