package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.pldt.itidm.core.utils.JdbcHelper.toOracleArray;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.SendSms;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;

public class SendSmsProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";
    private DataSource dataSource;

    public SendSmsProcedure(DataSource dataSource)
    {
        this.dataSource = checkNotNull(dataSource);
        setDataSource(this.dataSource);
        setSql("SEND_SMS");
        declareParameter(new SqlParameter("RCPNTS", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("MSG", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DISTRIBUTION_LIST_KEY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("DISTRIBUTION_LIST_VALUES", OracleTypes.ARRAY));
        declareParameter(new SqlParameter("ANONYMOUS_NUMBERS", OracleTypes.ARRAY));
        declareParameter(new SqlParameter("CREATEDBY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String send(SendSms sendSms)
    {
        checkNotNull(sendSms, "sendSms can't be null");
        ARRAY distListVals = toOracleArray(sendSms.getDistributionListValues(), "DIST_LIST_VALS_TYPE", dataSource);
        ARRAY anonymousNums = toOracleArray(sendSms.getAnonymousNumbers(), "MOBILE_NUMBERS_TYPE", dataSource);
        Object[] params = new Object[]
        {
            checkNotNull(emptyToNull(sendSms.getRecipients()), "sendSms.recipients can't be null"),
            checkNotNull(emptyToNull(sendSms.getMessage()), "sendSms.message can't be null"),
            emptyToNull(sendSms.getDistributionListKey()),
            distListVals,
            anonymousNums,
            checkNotNull(emptyToNull(sendSms.getCreatedBy()), "sendSms.createdBy can't be null")
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}