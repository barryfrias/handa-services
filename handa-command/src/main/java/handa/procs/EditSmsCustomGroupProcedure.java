package handa.procs;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.pldt.itidm.core.utils.JdbcHelper.toOracleArray;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.DistributionCustomGroup;
import oracle.jdbc.OracleTypes;

public class EditSmsCustomGroupProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    private DataSource dataSource;

    public EditSmsCustomGroupProcedure(DataSource dataSource)
    {
        this.dataSource = checkNotNull(dataSource);
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_SMS_DIST.EDIT_CUSTOM_GROUP");
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("P_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_DIST_VALUES", OracleTypes.ARRAY));
        declareParameter(new SqlParameter("P_MODIFIED_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String edit(DistributionCustomGroup customGroup)
    {
        checkNotNull(customGroup, "customGroup should not be null");
        checkNotNull(customGroup.getId(),"id should not be null");
        checkNotNull(customGroup.getName(),"name should not be null");
        checkArgument(customGroup.getValues() != null && customGroup.getValues().length > 0, "values should not be null or empty");
        checkNotNull(customGroup.getModifiedBy(),"modifiedBy should not be null");
        Object[] params = new Object[]
        {
            customGroup.getId(),
            customGroup.getName(),
            toOracleArray(customGroup.getValues(), "DIST_LIST_VALS_TYPE", dataSource),
            customGroup.getModifiedBy()
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}