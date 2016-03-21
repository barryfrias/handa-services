package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.DistributionList;
import handa.mappers.DistributionListRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSmsDistributionListProcedure
extends StoredProcedure
{
    private static final String RESULT = "out";

    public GetSmsDistributionListProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_SMS_DIST.DISTRIBUTION_LIST");
        declareParameter(new SqlParameter("P_TYPE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new DistributionListRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<DistributionList> list(String type)
    {
        Object[] params = new Object[]
        {
                checkNotNull(type, "type should not be null")
        };
        Map<String, Object> map = execute(params);
        List<DistributionList> list = (List<DistributionList>) map.get(RESULT);
        if(list == null) return ImmutableList.of();
        return list;
    }
}