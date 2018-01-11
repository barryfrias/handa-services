package handa.command;

import java.util.List;
import java.util.Map;

import handa.beans.dto.CallTree;
import handa.beans.dto.DashboardFilter;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;
import handa.beans.dto.SosPrompt;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

public interface CommandDAO
{
    Map<String, Integer> getPromptCount(String cty, String head, String dept, String comp, String startDate, String endDate);
    NewsFeed postNewsFeed(NewsFeed newsFeed);
    List<NewsFeed> getNewsFeeds(int pageNo);
    List<SosPrompt> getAllSos(String city, String startDate, String endDate);
    List<UserPrompt> getSos(String city, String startDate, String endDate);
    List<UserPrompt> getSafe(String city, String startDate, String endDate);
    List<UserReport> getUserReports(int pageNo);
    int getReportsCount();
    List<DashboardFilter> getCities();
    List<DashboardFilter> getDashboardHeads();
    List<DashboardFilter> getDashboardDepartments();
    List<DashboardFilter> getDashboardCompanies();
    List<UserPrompt> getNoResponse(String city, String startDate, String endDate);
    List<PromptCount> getSosCountPerCity(String startDate, String endDate);
    void resetEvents();
    String closePrompt(int id, ClosePrompt closePrompt);
    String updateSOS(int id, ClosePrompt closePrompt);
    int getUsersCount(String city);
    int deleteNewsFeed(int id, String deletedBy);
    NewsFeed updateNewsFeed(NewsFeed newsFeed);
    int closeUserReport(int id, CloseUserReport closeUserReport);
    List<SmsInboxMessage> getSmsInbox();
    List<SmsInboxMessage> getClutterSmsInbox();
    int readSms(int id, ReadSms readSms);
    int deleteSms(int id, String deletedBy);
    String sendSms(SendSms sendSms);
    List<SmsOutboxMessage> getSmsOutbox();
    int deleteSmsOutbox(int id, String deletedBy);
    List<DistributionList> getSmsDistributionList(String type);
    List<LovItem> getSmsDistributionLov(String distributionListCode);
    List<DistributionList> getNewsFeedsDistributionList(String type);
    List<LovItem> getNewsFeedsDistributionLov(String distributionListCode);
    String addNewsFeedsCustomGroup(DistributionCustomGroup customGroup);
    String editNewsFeedsCustomGroup(DistributionCustomGroup customGroup);
    String deleteNewsFeedsCustomGroup(long id);
    List<CallTree> listCallTree(Long id);
    long insertCallTree(CallTree callTree);
    String updateCallTree(CallTree callTree);
    String deleteCallTree(long id);
    String addSmsCustomGroup(DistributionCustomGroup customGroup);
    String editSmsCustomGroup(DistributionCustomGroup customGroup);
    String deleteSmsCustomGroup(long id);
}