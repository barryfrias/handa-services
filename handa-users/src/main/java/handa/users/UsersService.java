package handa.users;

import java.io.InputStream;
import java.util.List;

import com.google.common.base.Optional;

import handa.beans.dto.AuthInfo;
import handa.beans.dto.Company;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.LdapUser;
import handa.beans.dto.LdapUserSearch;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.User;
import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserRegistration;
import handa.beans.dto.UserReport;
import handa.beans.dto.UserSearch;
import handa.beans.dto.UserVerificationResult;
import handa.config.HandaUsersConstants.PromptType;

public interface UsersService
{
    String authByMobileNumber(AuthInfo authInfo, DeviceInfo deviceInfo);
    String authByMobileNumberAndUsername(AuthInfo authInfo, DeviceInfo deviceInfo);
    String prompt(UserPrompt userPrompt, PromptType promptType, DeviceInfo deviceInfo);
    Optional<UserInfo> getUserInfo(String mobileNumber);
    String report(DeviceInfo deviceInfo, UserReport userReport);
    String uploadFile(InputStream uploadedInputStream, String fileName);
    List<UserInfo> getUsers();
    String checkAppVersion(String versionString);
    List<UserInfo> searchByName(UserSearch userSearch);
    String addUser(User user);
    String editUser(User user);
    Optional<LdapUser> ldapSearchUser(LdapUserSearch userSearch);
    String register(UserRegistration registration, DeviceInfo deviceInfo);
    List<Company> getCompaniesLov();
    String registerDomainUser(UserRegistration userRegistration, DeviceInfo deviceInfo);
    UserVerificationResult verify(AuthInfo authInfo, DeviceInfo deviceInfo);
    String loginByPasscode(AuthInfo authInfo, DeviceInfo deviceInfo);
    List<NewsFeed> getPrivateNewsFeeds(String username, int pageNo);
}