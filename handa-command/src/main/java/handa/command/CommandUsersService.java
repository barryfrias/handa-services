package handa.command;

import java.util.List;
import java.util.Map;

import handa.beans.dto.UserLogin;

public interface CommandUsersService
{
    boolean authenticate(UserLogin userLogin);
    List<Map<String, Object>> registrations(String approvalStatus);
}