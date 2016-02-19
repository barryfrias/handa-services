package handa.command;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static handa.config.HandaCommandConstants.OK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

import handa.beans.dto.AppLog;
import handa.beans.dto.CallTree;
import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.core.DBLoggerDAO;
import handa.core.HandaProperties;

@Component
public class CommandServiceImpl
implements CommandService
{
    final static Logger log = LoggerFactory.getLogger(CommandServiceImpl.class);

    private String uploadDirectory;

    private CommandDAO commandDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public CommandServiceImpl(CommandDAO commandDAO, DBLoggerDAO dbLoggerDAO, HandaProperties handaProperties)
    {
        this.commandDAO = commandDAO;
        this.dbLoggerDAO = dbLoggerDAO;
        this.uploadDirectory = handaProperties.get("handa.command.upload.directory");
    }

    @Override
    public int getSosCount(String city)
    {
        return commandDAO.getSosCount(city);
    }

    @Override
    public int getSafeCount(String city)
    {
        return commandDAO.getSafeCount(city);
    }

    @Override
    public NewsFeed postNewsFeed(NewsFeed newsFeed)
    {
        NewsFeed feed = commandDAO.postNewsFeed(newsFeed);
        dbLoggerDAO.log(AppLog.server(newsFeed.getUsername(),"Posted news feed entry id %s", feed.getId()));
        return feed;
    }

    @Override
    public NewsFeed updateNewsFeed(NewsFeed newsFeed)
    {
        NewsFeed feed = commandDAO.updateNewsFeed(newsFeed);
        dbLoggerDAO.log(AppLog.server(newsFeed.getUsername(), "Updated news feed entry id %s", feed.getId()));
        return feed;
    }

    @Override
    public List<NewsFeed> getNewsFeeds(int pageNo)
    {
        return commandDAO.getNewsFeeds(pageNo);
    }

    @Override
    public List<NewsFeed> getPrivateNewsFeeds(String username, int pageNo)
    {
        return commandDAO.getPrivateNewsFeeds(username, pageNo);
    }

    @Override
    public int deleteNewsFeed(int id, String deletedBy)
    {
        int result = commandDAO.deleteNewsFeed(id, deletedBy);
        dbLoggerDAO.log(AppLog.server(deletedBy, "Deleted news feed id %s and result was %s", id, result));
        return result;
    }

    @Override
    public List<UserPrompt> getSos(String city)
    {
        return commandDAO.getSos(city);
    }

    @Override
    public List<UserPrompt> getSafe(String city)
    {
        return commandDAO.getSafe(city);
    }

    @Override
    public List<UserReport> getUserReports()
    {
        return commandDAO.getUserReports();
    }

    @Override
    public List<UserReport> getUserReports(int pageNo)
    {
        return commandDAO.getUserReports(pageNo);
    }

    @Override
    public int getReportsCount()
    {
        return commandDAO.getReportsCount();
    }

    @Override
    public List<City> getCities()
    {
        return commandDAO.getCities();
    }

    @Override
    public int getNoResponseCount(String city)
    {
        return commandDAO.getNoResponseCount(city);
    }

    @Override
    public List<UserPrompt> getNoResponse(String city)
    {
        return commandDAO.getNoResponse(city);
    }

    @Override
    public List<PromptCount> getSosCountPerCity()
    {
        return commandDAO.getSosCountPerCity();
    }

    @Override
    public String uploadFile(InputStream uploadedInputStream, String filename)
    {
        File directory = new File(uploadDirectory);
        File finalFile = new File(directory, checkNotNull(filename, "filename can't be null"));
        if(finalFile.isFile())
        {
            finalFile.delete();
        }
        File tempFile = new File(directory, filename + ".tmp");
        try(OutputStream out = new FileOutputStream(tempFile))
        {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1)
            {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            uploadedInputStream.close();
        }
        catch (IOException e)
        {
            log.error("IO Exception while processing uploaded file", e);
            return "IO ERROR OCCURED";
        }
        tempFile.renameTo(finalFile);
        return OK;
    }

    @Override
    public void resetEvents(String resetBy)
    {
        checkNotNull(emptyToNull(resetBy), "resetBy can't be null");
        commandDAO.resetEvents();
        dbLoggerDAO.log(AppLog.server(resetBy, "Events was reset by %s", resetBy));
    }

    @Override
    public List<UserLocation> getUsersLocations(String city)
    {
        return commandDAO.getUsersLocations(city);
    }

    @Override
    public int closePrompt(int id, ClosePrompt closePrompt)
    {
        int result = commandDAO.closePrompt(id, closePrompt);
        dbLoggerDAO.log(AppLog.server(closePrompt.getUsername(), "Closed prompt id %s and result was %s", id, result));
        return result;
    }

    @Override
    public int getUsersCount(String city)
    {
        return commandDAO.getUsersCount(city);
    }

    @Override
    public int closeUserReport(int id, CloseUserReport closeUserReport)
    {
        int result = commandDAO.closeUserReport(id, closeUserReport);
        dbLoggerDAO.log(AppLog.server(closeUserReport.getUsername(), "Closed user report id %s and result was %s", id, result));
        return result;
    }

    @Override
    public List<DistributionList> getNewsFeedDistributionList()
    {
        return commandDAO.getNewsFeedDistributionList();
    }

    @Override
    public List<LovItem> getNewsFeedsDistributionLov(String distributionListCode)
    {
        return commandDAO.getNewsFeedsDistributionLov(distributionListCode);
    }

    @Override
    public List<CallTree> list()
    {
        return commandDAO.list(null);
    }

    @Override
    public Optional<CallTree> getById(long id)
    {
        List<CallTree> list = commandDAO.list(id);
        if(list.isEmpty())
        {
            return absent();
        }
        return Optional.of(list.get(0));
    }

    @Override
    public long insertCallTree(CallTree callTree)
    {
        return commandDAO.insertCallTree(callTree);
    }
}