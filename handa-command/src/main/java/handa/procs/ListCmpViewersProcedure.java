package handa.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.collect.ImmutableList;

import handa.beans.dto.CmpViewer;
import oracle.jdbc.OracleTypes;

public class ListCmpViewersProcedure
extends StoredProcedure
{
    private static final String RESULT = "result";

    public ListCmpViewersProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("handa_cmp.cmp_viewers");
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new CmpViewerRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<CmpViewer> list()
    {
        Object[] params = new Object[]{};
        Map<String, Object> map = execute(params);
        List<CmpViewer> list = (List<CmpViewer>) map.get(RESULT);
        if(list == null || list.isEmpty()) return ImmutableList.of();
        return list;
    }

    class CmpViewerRowMapper
    implements RowMapper<CmpViewer>
    {
        @Override
        public CmpViewer mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            CmpViewer cmpViewer = new CmpViewer();
            cmpViewer.setRowNum(++rowNum);
            cmpViewer.setName(rs.getString("NAME"));
            cmpViewer.setUsername(rs.getString("AD_USERNAME"));
            cmpViewer.setCompany(rs.getString("COMPANY"));
            return cmpViewer;
        }
    }
}