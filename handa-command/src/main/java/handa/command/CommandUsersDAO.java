package handa.command;

import java.util.List;
import java.util.Map;

import handa.beans.dto.RegistrationAction;
import handa.beans.dto.RegistrationActionResult;

public interface CommandUsersDAO
{
    String checkUsername(String username);
    List<Map<String, Object>> registrations(Long registrationId, String approvalStatus);
    RegistrationActionResult registrationsAction(long registrationId, RegistrationAction action);
}