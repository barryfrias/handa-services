package handa.users;

import handa.HandaUsersSpringTestBase;
import handa.beans.dto.AuthInfo;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersDAOImplTest
extends HandaUsersSpringTestBase
{
    private static Logger log = LoggerFactory.getLogger(UsersDAOImplTest.class);

    @Autowired UsersDAO usersDAO;

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void testRegister()
    {
        AuthInfo userInfo = new AuthInfo()
        {{
            setMobileNumber("09321604267");
        }};
        String result = usersDAO.authByMobileNumber(userInfo);
        log.info("RESULT: {}", result);
    }
}