package handa.hrlink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import handa.beans.dto.AppLog;
import handa.beans.dto.DeviceInfo;
import handa.beans.dto.DtrInput;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;

@Component
public class HrLinkServiceImpl
implements HrLinkService
{
    private HrLinkDAO hrLinkDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public HrLinkServiceImpl(HrLinkDAO hrLinkDAO,
                            DBLoggerDAO dbLoggerDAO,
                            HandaProperties handaProperties)
    {
        this.hrLinkDAO = hrLinkDAO;
        this.dbLoggerDAO = dbLoggerDAO;
    }

    @Override
    public String timeIn(DeviceInfo deviceInfo, DtrInput dtrInput)
    {
        String result = hrLinkDAO.timeIn(dtrInput);
        AppLog applog = AppLog.client("N/A", dtrInput.getMobileNumber(), "HrLink: Time In activity. %s, %s, result=%s",
                                      deviceInfo, dtrInput, result);
        dbLoggerDAO.log(applog);
        return result;
    }

    @Override
    public String timeOut(DeviceInfo deviceInfo, DtrInput dtrInput)
    {
        String result = hrLinkDAO.timeOut(dtrInput);
        AppLog applog = AppLog.client("N/A", dtrInput.getMobileNumber(), "HrLink: Time Out activity. %s, %s, result=%s",
                                      deviceInfo, dtrInput, result);
        dbLoggerDAO.log(applog);
        return result;
    }
}