package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.CallTree;
import handa.core.JsonUtils;
import oracle.jdbc.OracleTypes;

public class UpdateCallTreeProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public UpdateCallTreeProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("HANDA_CALL_TREES.UPDATE_DATA");
        declareParameter(new SqlParameter("P_ID", OracleTypes.NUMBER));
        declareParameter(new SqlParameter("P_NAME", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_MOD_BY", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("P_JSON", OracleTypes.CLOB));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String update(CallTree callTree)
    {
        checkNotNull(callTree, "callTree can't be null");
        checkNotNull(callTree.getId(),"id should not be null");
        checkNotNull(callTree.getName(),"name should not be null");
        checkNotNull(callTree.getModifiedBy(),"modifiedBy should not be null");
        checkNotNull(callTree.getJsonData(),"jsonData should not be null");
        Object[] params = new Object[]
        {
             callTree.getId(),
             callTree.getName(),
             callTree.getModifiedBy(),
             new SqlLobValue(JsonUtils.stringify(callTree.getJsonData()))
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}