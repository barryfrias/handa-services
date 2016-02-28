package handa.core;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils
{
    private static ObjectMapper mapper = new ObjectMapper();

    public static final String stringify(Object json)
    {
        checkNotNull(json);
        try
        {
            return mapper.writeValueAsString(json);
        }
        catch(JsonProcessingException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public static final <T> T objectify(Reader reader, Class<T> valueType)
    {
        checkNotNull(reader);
        checkNotNull(valueType);
        try
        {
            return mapper.readValue(reader, valueType);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}