package handa.site;

import java.util.Map;

public interface CacheService
{
    Object get(String key);
    void put(String key, Object value);
    Map<String, Object> list();
}