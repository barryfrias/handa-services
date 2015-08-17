package handa.sms;

import handa.beans.dto.SmsInbound;


public interface SmsService
{
    String receive(SmsInbound smsInbound);
}