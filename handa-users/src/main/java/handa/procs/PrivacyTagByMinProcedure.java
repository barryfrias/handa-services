package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.AuthInfo;
import oracle.jdbc.OracleTypes;

public class PrivacyTagByMinProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public PrivacyTagByMinProcedure(DataSource dataSource)
    {
    	setDataSource(checkNotNull(dataSource));
        setSql("PRIVACY_TAGS_PRC");
        declareParameter(new SqlParameter("I_MOBILE_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }
    
    public String privacyTagByMin(AuthInfo authInfo)
    {
        checkNotNull(authInfo.getMobileNumber(), "mobileNumber can't be null");
        Map<String, Object> map = execute(authInfo.getMobileNumber());
        return (String) map.get(RESULT);
    }
}