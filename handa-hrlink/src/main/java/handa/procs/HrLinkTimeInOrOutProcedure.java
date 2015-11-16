package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.DtrInput;
import oracle.jdbc.OracleTypes;

public class HrLinkTimeInOrOutProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";
    private static final String IN = "I";
    private static final String OUT = "O";

    public HrLinkTimeInOrOutProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HRLINK_TIME_IN_OR_OUT");
        declareParameter(new SqlParameter("P_IN_OR_OUT", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_MOBILE_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_LATITUDE", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_LONGITUDE", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    private String call(String mode, String mobileNumber, String latitude, String longitude)
    {
        checkNotNull(mode, "mode should not be null");
        checkNotNull(mobileNumber, "mobileNumber should not be null");
        checkNotNull(latitude, "latitude should not be null");
        checkNotNull(longitude, "longitude should not be null");
        Object[] params =
        {
                mode,
                mobileNumber,
                latitude,
                longitude,
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }

    public String timeIn(DtrInput dtrInput)
    {
        checkNotNull(dtrInput, "dtrInput should not be null");
        return call(IN, dtrInput.getMobileNumber(), dtrInput.getLatitude(), dtrInput.getLongitude());
    }

    public String timeOut(DtrInput dtrInput)
    {
        checkNotNull(dtrInput, "dtrInput should not be null");
        return call(OUT, dtrInput.getMobileNumber(), dtrInput.getLatitude(), dtrInput.getLongitude());
    }
}