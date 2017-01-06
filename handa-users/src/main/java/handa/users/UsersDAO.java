package handa.users;

import java.util.List;

import com.google.common.base.Optional;

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
import handa.procs.DomainUserRegistrationProcedure.DomainRegistrationRequestResult;
import handa.procs.UserRegistrationProcedure.RegistrationRequestResult;

public interface UsersDAO
{
    String authByMobileNumber(AuthInfo authInfo);
    String authByMobileNumberAndUsername(AuthInfo authInfo);
    String prompt(UserPromptInput userPromptInput, PromptType promptType);
    Optional<UserInfo> getUserInfo(String mobileNumber);
    String report(DeviceInfo deviceInfo, UserReportInput userReportInput);
    List<UserInfo> getUsers();
    String checkAppVersion(String versionString);
    List<UserInfo> searchByName(UserSearch userSearch);
    String addUser(User user);
    String editUser(User user);
    String deleteUser(String mobileNumber, String createdDate, String deletedBy);
    List<Company> getCompaniesLov();
    RegistrationRequestResult register(UserRegistration registration);
    DomainRegistrationRequestResult registerDomainUser(UserRegistration userRegistration);
    UserVerificationResult verify(AuthInfo authInfo);
    String loginByPasscode(AuthInfo authInfo);
    List<NewsFeed> getPrivateNewsFeeds(String username, int pageNo);
    List<NewsFeed> getPrivateTips(String username, int pageNo);
    Subordinates getSubordinates(String mgrUsername, String startDate, String endDate);
    String privacyTagByMIN(AuthInfo authInfo);
    
}