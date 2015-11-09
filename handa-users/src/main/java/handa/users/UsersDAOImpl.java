package handa.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.beans.dto.UserSearch;
import handa.config.HandaUsersConstants.PromptType;
import handa.procs.AuthByMobileAndUsernameProcedure;
import handa.procs.AuthByMobileProcedure;
import handa.procs.CheckMobileAppVersionProcedure;
import handa.procs.SearchUserByNameProcedure;
import handa.procs.UserInfoProcedure;
import handa.procs.UserPromptProcedure;
import handa.procs.UserReportProcedure;

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
    private final SearchUserByNameProcedure searchUserByNameProcedure;

    @Autowired
    public UsersDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.authByMobileProcedure = new AuthByMobileProcedure(dataSource());
        this.authByMobileAndUsernameProcedure = new AuthByMobileAndUsernameProcedure(dataSource());
        this.userPromptProcedure = new UserPromptProcedure(dataSource());
        this.userInfoProcedure = new UserInfoProcedure(dataSource());
        this.userReportProcedure = new UserReportProcedure(dataSource());
        this.checkMobileAppVersionProcedure = new CheckMobileAppVersionProcedure(dataSource());
        this.searchUserByNameProcedure = new SearchUserByNameProcedure(dataSource());
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
    public Optional<UserInfo> getUserInfo(String mobileNumber)
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

    @Override
    public List<UserInfo> searchByName(UserSearch userSearch)
    {
        return searchUserByNameProcedure.search(userSearch);
    }
}