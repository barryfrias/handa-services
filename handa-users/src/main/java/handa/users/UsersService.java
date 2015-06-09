package handa.users;

import java.io.InputStream;
import java.util.List;

import com.google.common.base.Optional;

import handa.beans.dto.UserInfo;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.AuthInfo;
import handa.beans.dto.UserReport;
import handa.config.HandaUsersConstants.PromptType;

public interface UsersService
{
    String authByMobileNumber(AuthInfo authInfo);
    String authByMobileNumberAndUsername(AuthInfo authInfo);
    String prompt(UserPrompt userPrompt, PromptType promptType);
    Optional<UserInfo> getuserInfo(String mobileNumber);
    String report(UserReport userReport);
    String uploadFile(InputStream uploadedInputStream, String fileName);
    List<UserInfo> getUsers();
    String checkAppVersion(String versionString);
}