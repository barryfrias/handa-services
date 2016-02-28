package handa.users;

import java.util.List;

import com.google.common.base.Optional;

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

public interface UsersDAO
{
    String authByMobileNumber(AuthInfo authInfo);
    String authByMobileNumberAndUsername(AuthInfo authInfo);
    String prompt(UserPrompt userPrompt, PromptType promptType);
    Optional<UserInfo> getUserInfo(String mobileNumber);
    String report(DeviceInfo deviceInfo, UserReport userReport);
    List<UserInfo> getUsers();
    String checkAppVersion(String versionString);
    List<UserInfo> searchByName(UserSearch userSearch);
    List<City> getCitiesLov();
    List<Province> getProvincesLov();
    String addUser(User user);
    String editUser(User user);
    List<Company> getCompaniesLov();
    String register(UserRegistration registration);
    String registerDomainUser(UserRegistration userRegistration);
    UserVerificationResult verify(AuthInfo authInfo);
    String loginByPasscode(AuthInfo authInfo);
    List<NewsFeed> getPrivateNewsFeeds(String username, int pageNo);
}