package handa.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import handa.beans.dto.Cmp;

public class CmpRowMapper
implements RowMapper<Cmp>
{
    @Override
    public Cmp mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Cmp cmp = new Cmp();
        cmp.setRowNum(++rowNum);
        cmp.setFileId(rs.getLong("FILE_ID"));
        cmp.setFilename(rs.getString("FILENAME"));
        cmp.setName(rs.getString("NAME"));
        cmp.setUsername(rs.getString("USERNAME"));
        cmp.setCompany(rs.getString("COMPANY"));
        cmp.setDescription(rs.getString("DESCRIPTION"));
        cmp.setUploadedBy(rs.getString("UPLOADED_BY"));
        cmp.setUploadedDate(rs.getString("UPLOADED_DATE"));
        return cmp;
    }
}