package handa.command;

import java.util.List;
import java.util.Map;

import handa.beans.dto.RegistrationAction;
import handa.beans.dto.UserLogin;

public interface CommandUsersService
{
    boolean authenticate(UserLogin userLogin);
    List<Map<String, Object>> registrations(String approvalStatus);
    List<Map<String, Object>> registrationsById(long registrationId);
    String registrationsAction(long registrationId, RegistrationAction action);
}