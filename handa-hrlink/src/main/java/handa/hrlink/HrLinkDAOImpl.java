package handa.hrlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.User;
import handa.procs.AddUserProcedure;

@Component
public class HrLinkDAOImpl
extends AbstractJdbcDAO
implements HrLinkDAO
{
    private final AddUserProcedure addUserProcedure;

    @Autowired
    public HrLinkDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.addUserProcedure = new AddUserProcedure(dataSource());
    }


    @Override
    public String addUser(User user)
    {
        return addUserProcedure.add(user);
    }
}