package handa.command.procs;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.sql.DataSource;

import org.springframework.jdbc.object.StoredProcedure;

public class ResetEventsProcedure
extends StoredProcedure
{
    public ResetEventsProcedure(DataSource dataSource)
    {
        setDataSource(checkNotNull(dataSource));
        setSql("RESET_USERS_PROMPT");
        setFunction(false);
        compile();
    }

    public void reset()
    {
        execute((Object[])null);
    }
}