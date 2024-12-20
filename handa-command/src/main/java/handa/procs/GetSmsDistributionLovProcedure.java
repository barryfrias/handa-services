package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.LovItem;
import handa.mappers.DistributionLovRowMapper;
import oracle.jdbc.OracleTypes;

public class GetSmsDistributionLovProcedure
extends StoredProcedure
{
    private static final String RESULT = "out";

    public GetSmsDistributionLovProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_SMS_DIST.DISTRIBUTION_LOV");
        declareParameter(new SqlParameter("DIST_LIST_CODE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new DistributionLovRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<LovItem> list(String distributionListCode)
    {
        Object[] params = new Object[]
        {
                checkNotNull(distributionListCode, "distributionListCode can't be null")
        };
        Map<String, Object> map = execute(params);
        List<LovItem> list = (List<LovItem>) map.get(RESULT);
        if(list == null) return ImmutableList.of();
        return list;
    }
}