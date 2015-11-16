package handa.hrlink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.AppLog;
import handa.beans.dto.User;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;

@Component
public class HrLinkServiceImpl
implements HrLinkService
{
    final static Logger log = LoggerFactory.getLogger(HrLinkServiceImpl.class);

    private HrLinkDAO hrLinkDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public HrLinkServiceImpl(HrLinkDAO hrLinkDAO,
                            DBLoggerDAO dbLoggerDAO,
                            HandaProperties handaProperties)
    {
        this.hrLinkDAO = hrLinkDAO;
        this.dbLoggerDAO = dbLoggerDAO;
    }

    @Override
    public String addUser(User user)
    {
        String result = hrLinkDAO.addUser(user);
        dbLoggerDAO.log(AppLog.server(user.getModifiedBy(), "Tried to add user %s, result was %s", user.getAdUsername(), result));
        return result;
    }
}