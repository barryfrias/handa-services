package handa.sms;

import handa.beans.dto.SmsInbound;

public interface SmsDAO
{
    String receive(SmsInbound smsInbound);
}