package handa.command;

import handa.beans.dto.UserLogin;

public interface CommandUsersService
{
    boolean authenticate(UserLogin userLogin);
}