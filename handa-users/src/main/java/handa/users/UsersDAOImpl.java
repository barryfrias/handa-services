package handa.users;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.Company;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.Subordinates;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPromptInput;
import handa.beans.dto.UserRegistration;
import handa.beans.dto.UserReportInput;
import handa.beans.dto.UserSearch;
import handa.beans.dto.UserVerificationResult;
import handa.config.HandaUsersConstants.PromptType;
import handa.procs.AddUserProcedure;
import handa.procs.AuthByMobileAndUsernameProcedure;
import handa.procs.AuthByMobileProcedure;
import handa.procs.CheckMobileAppVersionProcedure;
import handa.procs.DeleteUserProcedure;
import handa.procs.DomainUserRegistrationProcedure;
import handa.procs.DomainUserRegistrationProcedure.DomainRegistrationRequestResult;
import handa.procs.EditUserProcedure;
import handa.procs.FilterFeedsProcedure;
import handa.procs.GetCompaniesLovProcedure;
import handa.procs.GetPrivateMobileNewsFeedsProcedure;
import handa.procs.GetPrivateNewsFeedsProcedure;
import handa.procs.GetPublicMobileNewsFeedsProcedure;
import handa.procs.SearchMobileNewsFeedsProcedure;
import handa.procs.GetSubordinatesProcedure;
import handa.procs.LoginByPasscodeProcedure;
import handa.procs.PrivacyTagByMinProcedure;
import handa.procs.SearchUserByNameProcedure;
import handa.procs.UserInfoProcedure;
import handa.procs.UserPromptProcedure;
import handa.procs.UserRegistrationProcedure;
import handa.procs.UserRegistrationProcedure.RegistrationRequestResult;
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
    private final GetCompaniesLovProcedure getCompaniesLovProcedure;
    private final AddUserProcedure addUserProcedure;
    private final EditUserProcedure editUserProcedure;
    private final DeleteUserProcedure deleteUserProcedure;
    private final UserRegistrationProcedure userRegistrationProcedure;
    private final DomainUserRegistrationProcedure domainUserRegistrationProcedure;
    private final VerifyUserAndAuthMethodProcedure verifyUserAndAuthMethodProcedure;
    private final LoginByPasscodeProcedure loginByPasscodeProcedure;
    private final GetPrivateNewsFeedsProcedure getPrivateNewsFeedsProcedure;
    private final GetPublicMobileNewsFeedsProcedure getPublicMobileNewsFeedsProcedure;
    private final GetPrivateMobileNewsFeedsProcedure getPrivateMobileNewsFeedsProcedure;
    private final FilterFeedsProcedure filterFeedsProcedure;
    private final GetSubordinatesProcedure getSubordinatesProcedure;
    private final PrivacyTagByMinProcedure privacyTagByMinProcedure;
    private final SearchMobileNewsFeedsProcedure searchPublicNewsFeedsMobileProcedure;

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
        this.getCompaniesLovProcedure = new GetCompaniesLovProcedure(dataSource());
        this.addUserProcedure = new AddUserProcedure(dataSource());
        this.editUserProcedure = new EditUserProcedure(dataSource());
        this.deleteUserProcedure = new DeleteUserProcedure(dataSource());
        this.userRegistrationProcedure = new UserRegistrationProcedure(dataSource());
        this.domainUserRegistrationProcedure = new DomainUserRegistrationProcedure(dataSource());
        this.verifyUserAndAuthMethodProcedure = new VerifyUserAndAuthMethodProcedure(dataSource());
        this.loginByPasscodeProcedure = new LoginByPasscodeProcedure(dataSource());
        this.getPrivateNewsFeedsProcedure = new GetPrivateNewsFeedsProcedure(dataSource());
        this.getPublicMobileNewsFeedsProcedure = new GetPublicMobileNewsFeedsProcedure(dataSource());
        this.searchPublicNewsFeedsMobileProcedure = new SearchMobileNewsFeedsProcedure(dataSource());
        this.getPrivateMobileNewsFeedsProcedure = new GetPrivateMobileNewsFeedsProcedure(dataSource());
        this.filterFeedsProcedure = new FilterFeedsProcedure(dataSource());
        this.getSubordinatesProcedure = new GetSubordinatesProcedure(dataSource());
        this.privacyTagByMinProcedure = new PrivacyTagByMinProcedure(dataSource());
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
    public String prompt(UserPromptInput userPromptInput, PromptType promptType)
    {
        return userPromptProcedure.prompt(userPromptInput, promptType);
    }

    @Override
    public Optional<UserInfo> getUserInfo(String mobileNumber)
    {
        return userInfoProcedure.getInfo(mobileNumber);
    }

    @Override
    public String report(DeviceInfo deviceInfo, UserReportInput userReportInput)
    {
        return userReportProcedure.report(deviceInfo, userReportInput);
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
    public String deleteUser(String mobileNumber, String createdDate, String deletedBy)
    {
        return deleteUserProcedure.delete(mobileNumber, createdDate, deletedBy);
    }

    @Override
    public List<Company> getCompaniesLov()
    {
        return getCompaniesLovProcedure.list();
    }

    @Override
    public RegistrationRequestResult register(UserRegistration registration)
    {
        return userRegistrationProcedure.register(registration);
    }

    @Override
    public DomainRegistrationRequestResult registerDomainUser(UserRegistration userRegistration)
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

    @Override
    public List<NewsFeed> getPublicNewsFeedsMobile(String username, int pageNo)
    {
        return getPublicMobileNewsFeedsProcedure.list(username, pageNo);
    }

    @Override
    public List<NewsFeed> searchPublicNewsFeedsMobile(String username, Map<String, Object> json)
    {
        checkNotNull(json, "json should not be null");
        return searchPublicNewsFeedsMobileProcedure.search(username, (int)json.get("pageNo"), (String)json.get("keyword"), "PUBLIC");
    }

    @Override
    public List<NewsFeed> searchPrivateNewsFeedsMobile(String username, Map<String, Object> json)
    {
        checkNotNull(json, "json should not be null");
        return searchPublicNewsFeedsMobileProcedure.search(username, (int)json.get("pageNo"), (String)json.get("keyword"), "PRIVATE");
    }

    @Override
    public List<NewsFeed> getPrivateNewsFeedsMobile(String username, int pageNo)
    {
        return getPrivateMobileNewsFeedsProcedure.list(username, pageNo);
    }

    @Override
    public List<NewsFeed> getPrivateTips(String username, int pageNo)
    {
        return filterFeedsProcedure.list(username, "tips", pageNo);
    }

    @Override
    public Subordinates getSubordinates(String mgrUsername, String startDate, String endDate)
    {
    	return getSubordinatesProcedure.subordinatesList(mgrUsername, startDate, endDate);
    }

    @Override
    public String privacyTagByMIN(AuthInfo authInfo)
    {
        return privacyTagByMinProcedure.privacyTagByMin(authInfo);
    }
}