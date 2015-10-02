package handa.sms;

import handa.beans.dto.SmsInbound;

import com.google.common.base.Optional;

import handa.beans.dto.SendSmsInput;
import handa.beans.dto.SendSmsOutput;


public interface SmsService
{
    String receive(SmsInbound smsInbound);
    Optional<SendSmsOutput> send(SendSmsInput sendSmsInput);
}