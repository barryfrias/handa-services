package handa.hrlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.DtrInput;
import handa.procs.HrLinkTimeInOrOutProcedure;

@Component
public class HrLinkDAOImpl
extends AbstractJdbcDAO
implements HrLinkDAO
{
    private final HrLinkTimeInOrOutProcedure hrLinkTimeInOrOutProcedure;

    @Autowired
    public HrLinkDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.hrLinkTimeInOrOutProcedure = new HrLinkTimeInOrOutProcedure(dataSource());
    }

    @Override
    public String timeIn(DtrInput dtrInput)
    {
        return hrLinkTimeInOrOutProcedure.timeIn(dtrInput);
    }

    @Override
    public String timeOut(DtrInput dtrInput)
    {
        return hrLinkTimeInOrOutProcedure.timeOut(dtrInput);
    }
}