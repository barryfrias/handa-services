package handa.site;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class CacheServiceImpl implements CacheService
{
    private Map<String, Object> cache;

    public CacheServiceImpl()
    {
        if(cache == null)
        {
            cache = new ConcurrentHashMap<>();
        }
    }

    @Override
    public Object get(String key)
    {
        checkNotNull(key, "key should not be null.");
        Object value = cache.get(key);
        return value;
    }

    @Override
    public void put(String key, Object value)
    {
        checkNotNull(key, "key should not be null.");
        if(value == null)
        {
            cache.remove(key);
            return;
        }
        cache.put(key, value);
    }

    @Override
    public Map<String, Object> list()
    {
        return cache;
    }
}
