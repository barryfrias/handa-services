package handa.beans.dto;


import static com.google.common.base.MoreObjects.toStringHelper;

import javax.ws.rs.core.HttpHeaders;

public class DeviceInfo
{
    private String deviceId;
    private String deviceType;
    private String appVersion;
    private String batteryLevel;

    private DeviceInfo() {}

    public static DeviceInfo from(HttpHeaders headers)
    {
        DeviceInfo me = new DeviceInfo();
        if(headers != null)
        {
            String value = headers.getHeaderString("Device-Id");
            me.deviceId = value;

            value = headers.getHeaderString("Device-Type");
            me.deviceType = value;

            value = headers.getHeaderString("App-Version");
            me.appVersion = value;

            value = headers.getHeaderString("Battery-Level");
            me.batteryLevel = value;
        }
        return me;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    public String getBatteryLevel()
    {
        return batteryLevel;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
               .add("deviceId", deviceId)
               .add("deviceType", deviceType)
               .add("appVersion", appVersion)
               .add("batteryLevel", batteryLevel)
               .toString();
    }
}