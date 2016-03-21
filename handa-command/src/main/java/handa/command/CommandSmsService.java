package handa.command;

import java.util.List;

import handa.beans.dto.LovItem;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
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
    List<DistributionList> getSmsDistributionList();
    List<DistributionList> getCustomSmsDistributionList();
    List<LovItem> getSmsDistributionLov(String distributionListCode);
    String addSmsCustomGroup(DistributionCustomGroup customGroup);
    String editSmsCustomGroup(DistributionCustomGroup customGroup);
    String deleteSmsCustomGroup(long id, String deletedBy);
}