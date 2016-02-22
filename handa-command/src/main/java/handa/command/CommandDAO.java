package handa.command;

import java.util.List;

import handa.beans.dto.CallTree;
import handa.beans.dto.City;
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
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

public interface CommandDAO
{
    int getSosCount(String city);
    int getSafeCount(String city);
    NewsFeed postNewsFeed(NewsFeed newsFeed);
    List<NewsFeed> getNewsFeeds(int pageNo);
    List<UserPrompt> getSos(String city);
    List<UserPrompt> getSafe(String city);
    List<UserReport> getUserReports();
    List<UserReport> getUserReports(int pageNo);
    int getReportsCount();
    List<City> getCities();
    int getNoResponseCount(String city);
    List<UserPrompt> getNoResponse(String city);
    List<PromptCount> getSosCountPerCity();
    void resetEvents();
    List<UserLocation> getUsersLocations(String city);
    int closePrompt(int id, ClosePrompt closePrompt);
    int getUsersCount(String city);
    int deleteNewsFeed(int id, String deletedBy);
    NewsFeed updateNewsFeed(NewsFeed newsFeed);
    int closeUserReport(int id, CloseUserReport closeUserReport);
    List<SmsInboxMessage> getSmsInbox();
    int readSms(int id, ReadSms readSms);
    int deleteSms(int id, String deletedBy);
    String sendSms(SendSms sendSms);
    List<SmsOutboxMessage> getSmsOutbox();
    int deleteSmsOutbox(int id, String deletedBy);
    List<DistributionList> getSmsDistributionList();
    List<LovItem> getSmsDistributionLov(String distributionListCode);
    List<DistributionList> getNewsFeedsDistributionList(String type);
    List<LovItem> getNewsFeedsDistributionLov(String distributionListCode);
    String addNewsFeedsCustomGroup(DistributionCustomGroup customGroup);
    String editNewsFeedsCustomGroup(DistributionCustomGroup customGroup);
    String deleteNewsFeedsCustomGroup(long id);
    List<CallTree> list(Long id);
    long insertCallTree(CallTree callTree);
    String updateCallTree(CallTree callTree);
    String deleteCallTree(long id);
}