package handa.command;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import handa.beans.dto.CallTree;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.Cmp;
import handa.beans.dto.CmpViewer;
import handa.beans.dto.DashboardFilter;
import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.NewsFeedSearch;
import handa.beans.dto.PromptCount;
import handa.beans.dto.SosPrompt;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

public interface CommandService
{
    Map<String, Integer> getPromptCount(String cty, String head, String dept, String comp, String startDate, String endDate);
    NewsFeed postNewsFeed(NewsFeed newsFeed);
    List<NewsFeed> getNewsFeeds(int pageNo);
    List<NewsFeed> searchNewsFeed(NewsFeedSearch newsFeedSearch);
    List<SosPrompt> getAllSos(String cty, String head, String dept, String comp, String startDate, String endDate);
    List<UserPrompt> getSos(String cty, String head, String dept, String comp, String startDate, String endDate);
    List<UserPrompt> getSafe(String cty, String head, String dept, String comp, String startDate, String endDate);
    List<UserPrompt> getNoResponse(String cty, String head, String dept, String comp, String startDate, String endDate);
    List<UserReport> getUserReports(int pageNo);
    int getReportsCount();
    List<DashboardFilter> getCities();
    List<DashboardFilter> getDashboardHeads();
    List<DashboardFilter> getDashboardDepartments();
    List<DashboardFilter> getDashboardCompanies();
    List<PromptCount> getSosCountPerCity(String startDate, String endDate);
    String uploadFile(InputStream uploadedInputStream, String name);
    void resetEvents(String resetBy);
    String closePrompt(int id, ClosePrompt closePrompt);
    String updateSOS(int id, ClosePrompt closePrompt);
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
	String addCmp(Cmp cmp);
    String editCmp(Cmp cmp);
    String deleteCmp(long fileId, String deletedBy);
    List<Cmp> listCmp();
    List<CmpViewer> listCmpViewers();
}