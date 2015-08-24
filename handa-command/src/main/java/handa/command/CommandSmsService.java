package handa.command;

import java.util.List;

import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsDistributionList;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;

public interface CommandSmsService
{
    List<SmsInboxMessage> getSmsInbox();
    int readSmsInbox(int id, ReadSms readSms);
    int deleteSmsInbox(int id, String deletedBy);
    String sendSms(SendSms sendSms);
    List<SmsOutboxMessage> getSmsOutbox();
    int deleteSmsOutbox(int id, String deletedBy);
    List<SmsDistributionList> getSmsDistributionList();
}