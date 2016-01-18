package handa.hrlink;

import handa.beans.dto.DeviceInfo;
import handa.beans.dto.DtrInput;

public interface HrLinkService
{
    String timeIn(DeviceInfo deviceInfo, DtrInput dtrInput);
    String timeOut(DeviceInfo deviceInfo, DtrInput dtrInput);
}