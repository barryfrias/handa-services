package handa.procs;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import handa.beans.dto.UserInfo;
import handa.mappers.UserInfoRowMapper;
import oracle.jdbc.OracleTypes;

public class UserInfoProcedure
extends StoredProcedure
{
    private static final String RESULT = "RESULT";

    public UserInfoProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("GET_USER_INFO");
        declareParameter(new SqlParameter("MOB_NO", OracleTypes.VARCHAR));
        declareParameter(new SqlOutParameter(RESULT, OracleTypes.CURSOR, new UserInfoRowMapper()));
        setFunction(false);
        compile();
    }

    @SuppressWarnings("unchecked")
    public Optional<UserInfo> getInfo(String mobileNumber)
    {
        checkNotNull(mobileNumber, "mobileNumber can't be null");
        Map<String, Object> map = execute(mobileNumber);
        List<UserInfo> list = (List<UserInfo>) map.get(RESULT);
        if(list.isEmpty())
        {
            return absent();
        }
        return of(Iterables.getOnlyElement(list));
    }

    @SuppressWarnings("unchecked")
    public List<UserInfo> list()
    {
        Map<String, Object> map = execute((Object)null);
        List<UserInfo> list = (List<UserInfo>) map.get(RESULT);
        return list;
    }
}