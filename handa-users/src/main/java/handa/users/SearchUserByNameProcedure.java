package handa.users;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import handa.beans.dto.UserInfo;
import handa.beans.dto.UserSearch;
import handa.users.mappers.UserInfoRowMapper;
import oracle.jdbc.OracleTypes;

public class SearchUserByNameProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public SearchUserByNameProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("SEARCH_USER_BY_NAME");
        declareParameter(new SqlParameter("KEYWORD", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserInfoRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public List<UserInfo> search(UserSearch userSearch)
    {
        checkNotNull(userSearch, "userSearch can't be null");
        checkNotNull(userSearch.getNameKeyword(), "userSearch.nameKeyword can't be null");
        Map<String, Object> map = execute(userSearch.getNameKeyword());
        return (List<UserInfo>) map.get(RESULT);
    }
}