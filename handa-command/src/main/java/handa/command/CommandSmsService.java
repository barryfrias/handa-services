package handa.command;

import java.util.List;

import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsMessage;

public interface CommandSmsService
{
    List<SmsMessage> getSmsInbox();
    int readSmsInbox(int id, ReadSms readSms);
    int deleteSmsInbox(int id, String deletedBy);
    String sendSms(SendSms sendSms);
}