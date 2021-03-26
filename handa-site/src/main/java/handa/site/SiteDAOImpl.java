package handa.site;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.procs.AddressLovProcedure;
import handa.procs.AddressLovProcedure.AddressContext;

@Component
public class SiteDAOImpl
extends AbstractJdbcDAO
implements SiteDAO
{
    private final AddressLovProcedure addressLovProcedure;

    @Autowired
    public SiteDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.addressLovProcedure = new AddressLovProcedure(dataSource());
    }

    @Override
    public List<Map<String, Object>> getProvinces()
    {
        return addressLovProcedure.list(AddressContext.PROVINCES, null, null);
    }

    @Override
    public List<Map<String, Object>> getCities(String province)
    {
        checkNotNull(emptyToNull(province), "province should not be null");
        return addressLovProcedure.list(AddressContext.CITIES, province, null);
    }

    @Override
    public List<Map<String, Object>> getBarangays(String province, String city)
    {
        checkNotNull(emptyToNull(province), "province should not be null");
        checkNotNull(emptyToNull(city), "city should not be null");
        return addressLovProcedure.list(AddressContext.BARANGAYS, province, city);
    }
}