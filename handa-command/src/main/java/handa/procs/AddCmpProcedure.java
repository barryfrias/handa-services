package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.Cmp;
import oracle.jdbc.OracleTypes;

public class AddCmpProcedure
extends StoredProcedure
{
    private static final String RESULT = "result";

    public AddCmpProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_cmp.add_cmp");
        declareParameter(new SqlParameter("p_filename", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_viewer", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_description", OracleTypes.VARCHAR));
        declareParameter(new SqlParameter("p_uploaded_by", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.VARCHAR));
        setFunction(false);
        compile();
    }

    public String add(Cmp cmp)
    {
        checkNotNull(cmp, "cmp should not be null");
        checkNotNull(cmp.getFilename(),"name filename not be null");
        checkNotNull(cmp.getViewer(),"name viewer not be null");
        checkNotNull(cmp.getUploadedBy(),"uploadedBy should not be null");
        Object[] params = new Object[]
        {
            cmp.getFilename(),
            cmp.getViewer(),
            cmp.getDescription(),
            cmp.getUploadedBy(),
        };
        Map<String, Object> map = execute(params);
        return (String) map.get(RESULT);
    }
}