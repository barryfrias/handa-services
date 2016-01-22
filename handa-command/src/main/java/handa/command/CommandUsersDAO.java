package handa.command;

import java.util.List;
import java.util.Map;

import handa.beans.dto.RegistrationAction;

public interface CommandUsersDAO
{
    String checkUsername(String username);
    List<Map<String, Object>> registrations(Long registrationId, String approvalStatus);
    String registrationsAction(long registrationId, RegistrationAction action);
}