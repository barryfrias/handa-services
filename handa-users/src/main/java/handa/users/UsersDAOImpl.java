package handa.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.City;
import handa.beans.dto.Company;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.Province;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserRegistration;
import handa.beans.dto.UserReport;
import handa.beans.dto.UserSearch;
import handa.beans.dto.UserVerificationResult;
import handa.config.HandaUsersConstants.PromptType;
import handa.procs.AddUserProcedure;
import handa.procs.AuthByMobileAndUsernameProcedure;
import handa.procs.AuthByMobileProcedure;
import handa.procs.CheckMobileAppVersionProcedure;
import handa.procs.DomainUserRegistrationProcedure;
import handa.procs.EditUserProcedure;
import handa.procs.GetCitiesLovProcedure;
import handa.procs.GetCompaniesLovProcedure;
import handa.procs.GetPrivateNewsFeedsProcedure;
import handa.procs.GetProvincesLovProcedure;
import handa.procs.SearchUserByNameProcedure;
import handa.procs.UserInfoProcedure;
import handa.procs.LoginByPasscodeProcedure;
import handa.procs.UserPromptProcedure;
import handa.procs.UserRegistrationProcedure;
import handa.procs.UserReportProcedure;
import handa.procs.VerifyUserAndAuthMethodProcedure;

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
    private final GetCitiesLovProcedure getCitiesLovProcedure;
    private final GetProvincesLovProcedure getProvincesLovProcedure;
    private final GetCompaniesLovProcedure getCompaniesLovProcedure;
    private final AddUserProcedure addUserProcedure;
    private final EditUserProcedure editUserProcedure;
    private final UserRegistrationProcedure userRegistrationProcedure;
    private final DomainUserRegistrationProcedure domainUserRegistrationProcedure;
    private final VerifyUserAndAuthMethodProcedure verifyUserAndAuthMethodProcedure;
    private final LoginByPasscodeProcedure loginByPasscodeProcedure;
    private final GetPrivateNewsFeedsProcedure getPrivateNewsFeedsProcedure;

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
        this.getCitiesLovProcedure = new GetCitiesLovProcedure(dataSource());
        this.getProvincesLovProcedure = new GetProvincesLovProcedure(dataSource());
        this.getCompaniesLovProcedure = new GetCompaniesLovProcedure(dataSource());
        this.addUserProcedure = new AddUserProcedure(dataSource());
        this.editUserProcedure = new EditUserProcedure(dataSource());
        this.userRegistrationProcedure = new UserRegistrationProcedure(dataSource());
        this.domainUserRegistrationProcedure = new DomainUserRegistrationProcedure(dataSource());
        this.verifyUserAndAuthMethodProcedure = new VerifyUserAndAuthMethodProcedure(dataSource());
        this.loginByPasscodeProcedure = new LoginByPasscodeProcedure(dataSource());
        this.getPrivateNewsFeedsProcedure = new GetPrivateNewsFeedsProcedure(dataSource());
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
    public String loginByPasscode(AuthInfo authInfo)
    {
        return loginByPasscodeProcedure.login(authInfo);
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
    public String report(DeviceInfo deviceInfo, UserReport userReport)
    {
        return userReportProcedure.report(deviceInfo, userReport);
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

    @Override
    public List<City> getCitiesLov()
    {
        return getCitiesLovProcedure.list();
    }

    @Override
    public List<Province> getProvincesLov()
    {
        return getProvincesLovProcedure.list();
    }

    @Override
    public String addUser(User user)
    {
        return addUserProcedure.add(user);
    }

    @Override
    public String editUser(User user)
    {
        return editUserProcedure.edit(user);
    }

    @Override
    public List<Company> getCompaniesLov()
    {
        return getCompaniesLovProcedure.list();
    }

    @Override
    public String register(UserRegistration registration)
    {
        return userRegistrationProcedure.register(registration);
    }

    @Override
    public String registerDomainUser(UserRegistration userRegistration)
    {
        return domainUserRegistrationProcedure.register(userRegistration);
    }

    @Override
    public UserVerificationResult verify(AuthInfo authInfo)
    {
        return verifyUserAndAuthMethodProcedure.invoke(authInfo);
    }

    @Override
    public List<NewsFeed> getPrivateNewsFeeds(String username, int pageNo)
    {
        return getPrivateNewsFeedsProcedure.list(username, pageNo);
    }
}