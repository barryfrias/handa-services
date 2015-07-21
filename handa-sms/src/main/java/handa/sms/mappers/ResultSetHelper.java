package handa.sms.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ResultSetHelper
{
    private static final String INVALID_COLUMN_NAME = "Invalid column name";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MMM-yy");

    public static String dateToString(ResultSet rs, String colName) throws SQLException
    {
        try
        {
            Timestamp timeStamp = rs.getTimestamp(colName);
            if(timeStamp != null)
            {
                return DATE_FORMATTER.format(timeStamp);
            }
            return null;
        }
        catch(SQLException ex)
        {
            if(INVALID_COLUMN_NAME.equals(ex.getMessage()))
            {
                return "Invalid column name: " + colName;
            }
            throw ex;
        }
    }

    public static String getString(ResultSet rs, String columnName) throws SQLException
    {
        try
        {
           return rs.getString(columnName);
        }
        catch(SQLException ex)
        {
            if(INVALID_COLUMN_NAME.equals(ex.getMessage()))
            {
                return "Invalid column name: " + columnName;
            }
            throw ex;
        }
    }

    public static int getInt(ResultSet rs, String columnName) throws SQLException
    {
        try
        {
           return rs.getInt(columnName);
        }
        catch(SQLException ex)
        {
            if(INVALID_COLUMN_NAME.equals(ex.getMessage()))
            {
                return Integer.MIN_VALUE;
            }
            throw ex;
        }
    }
}