package handa.command;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import handa.beans.dto.CallTree;
import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.SosPrompt;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

public interface CommandService
{
    Map<String, Integer> getPromptCount(String city, String startDate, String endDate);
    NewsFeed postNewsFeed(NewsFeed newsFeed);
    List<NewsFeed> getNewsFeeds(int pageNo);
    List<SosPrompt> getAllSos();
    List<UserPrompt> getSos(String city, String startDate, String endDate);
    List<UserPrompt> getSafe(String city, String startDate, String endDate);
    List<UserReport> getUserReports();
    List<UserReport> getUserReports(int pageNo);
    int getReportsCount();
    List<City> getCities();
    List<UserPrompt> getNoResponse(String city, String startDate, String endDate);
    List<PromptCount> getSosCountPerCity(String startDate, String endDate);
    String uploadFile(InputStream uploadedInputStream, String name);
    void resetEvents(String resetBy);
    List<UserLocation> getUsersLocations(String city, String startDate, String endDate);
    String closePrompt(int id, ClosePrompt closePrompt);
    int getUsersCount(String city);
    int deleteNewsFeed(int id, String deletedBy);
    NewsFeed updateNewsFeed(NewsFeed newsFeed);
    int closeUserReport(int id, CloseUserReport closeUserReport);
    List<DistributionList> getNewsFeedsDistributionList();
    List<DistributionList> getCustomNewsFeedsDistributionList();
    List<LovItem> getNewsFeedsDistributionLov(String distributionListCode);
    String addNewsFeedsCustomGroup(DistributionCustomGroup customGroup);
    String editNewsFeedsCustomGroup(DistributionCustomGroup customGroup);
    String deleteNewsFeedsCustomGroup(long id, String deletedBy);
    List<CallTree> listCallTree();
    Optional<CallTree> getCallTreeById(long id);
    long insertCallTree(CallTree callTree);
    String updateCallTree(CallTree callTree);
    String deleteCallTree(long id, String deletedBy);
}