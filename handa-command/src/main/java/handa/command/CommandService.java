package handa.command;

import java.io.InputStream;
import java.util.List;

import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;

public interface CommandService
{
    int getSosCount(String city);
    int getSafeCount(String city);
    NewsFeed postNewsFeed(NewsFeed newsFeed);
    List<NewsFeed> getNewsFeeds();
    List<UserPrompt> getSos(String city);
    List<UserPrompt> getSafe(String city);
    List<UserReport> getUserReports();
    int getReportsCount();
    List<City> getCities();
    int getNoResponseCount(String city);
    List<UserPrompt> getNoResponse(String city);
    List<PromptCount> getSosCountPerCity();
    String uploadFile(InputStream uploadedInputStream, String name);
    void resetEvents(String resetBy);
    List<UserLocation> getUsersLocations(String city);
    int closePrompt(int id, ClosePrompt closePrompt);
    int getUsersCount(String city);
    int deleteNewsFeed(int id, String deletedBy);
    NewsFeed updateNewsFeed(NewsFeed newsFeed);
}