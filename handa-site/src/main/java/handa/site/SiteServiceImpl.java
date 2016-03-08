package handa.site;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiteServiceImpl implements SiteService
{
    private final SiteDAO siteDAO;

    @Autowired
    public SiteServiceImpl(SiteDAO siteDAO)
    {
        this.siteDAO = siteDAO;
    }

    @Override
    public List<Map<String, Object>> getProvinces()
    {
        return siteDAO.getProvinces();
    }

    @Override
    public List<Map<String, Object>> getCities(String province)
    {
        return siteDAO.getCities(province);
    }

    @Override
    public List<Map<String, Object>> getBarangays(String province, String city)
    {
        return siteDAO.getBarangays(province, city);
    }
}
