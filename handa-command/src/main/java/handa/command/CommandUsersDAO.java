package handa.command;

import java.util.List;
import java.util.Map;

public interface CommandUsersDAO
{
    String checkUsername(String username);
    List<Map<String, Object>> registrations(Long registrationId, String approvalStatus);
}