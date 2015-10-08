package handa.core;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class HandaProperties
implements ApplicationContextAware
{
    private ListPropertiesProcedure proc;
    private Map<String, String> map;
    private ApplicationContext applicationContext;

    public HandaProperties(DataSource dataSource)
    {
        this.proc = new ListPropertiesProcedure(dataSource);
        this.map = proc.propertiesMap();
    }

    public String get(String key)
    {
        return map.get(key);
    }

    public String[] getArray(String key)
    {
        String val = map.get(key);
        if(val == null) return new String[0];
        return val.split(",");
    }

    public void reload()
    {
        this.map = proc.propertiesMap();
        ((ConfigurableApplicationContext) this.applicationContext).refresh();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}