package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.core.ToMapRowMapper;
import oracle.jdbc.OracleTypes;

public class AddressLovProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";
    public static enum AddressContext { PROVINCES, CITIES, BARANGAYS };

    public AddressLovProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_ADDRESS_LOV.LIST");
        declareParameter(new SqlParameter("P_PROVINCE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_CITY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_CONTEXT_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new ToMapRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> list(AddressContext contextName, String province, String city)
    {
        Object[] params = new Object[]
        {
            province,
            city,
            contextName.toString()
        };
        Map<String, Object> map = execute(params);
        return (List<Map<String, Object>>) map.get(RESULT);
    }
}