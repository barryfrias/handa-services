package handa.site;

import java.util.List;
import java.util.Map;

public interface SiteService
{
    List<Map<String, Object>> getProvinces();
    List<Map<String, Object>> getCities(String province);
    List<Map<String, Object>> getBarangays(String province, String city);
}