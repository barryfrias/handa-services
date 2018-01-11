package handa.command;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;
import static handa.config.HandaCommandConstants.OK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

import handa.beans.dto.AppLog;
import handa.beans.dto.CallTree;
import handa.beans.dto.DashboardFilter;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.DistributionCustomGroup;
import handa.beans.dto.DistributionList;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.SosPrompt;
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
    public Map<String, Integer> getPromptCount(String city, String startDate, String endDate)
    {
        return commandDAO.getPromptCount(city, startDate, endDate);
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
    public int deleteNewsFeed(int id, String deletedBy)
    {
        int result = commandDAO.deleteNewsFeed(id, deletedBy);
        dbLoggerDAO.log(AppLog.server(deletedBy, "Deleted news feed id %s and result was %s", id, result));
        return result;
    }

    @Override
    public List<SosPrompt> getAllSos(String city, String startDate, String endDate)
    {
        return commandDAO.getAllSos(city, startDate, endDate);
    }

    @Override
    public List<UserPrompt> getSos(String city, String startDate, String endDate)
    {
        return commandDAO.getSos(city, startDate, endDate);
    }

    @Override
    public List<UserPrompt> getSafe(String city, String startDate, String endDate)
    {
        return commandDAO.getSafe(city, startDate, endDate);
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
    public List<DashboardFilter> getCities()
    {
        return commandDAO.getCities();
    }

    @Override
    public List<DashboardFilter> getDashboardHeads()
    {
        return commandDAO.getDashboardHeads();
    }

    @Override
    public List<DashboardFilter> getDashboardDepartments()
    {
        return commandDAO.getDashboardDepartments();
    }

    @Override
    public List<DashboardFilter> getDashboardCompanies()
    {
        return commandDAO.getDashboardCompanies();
    }

    @Override
    public List<UserPrompt> getNoResponse(String city, String startDate, String endDate)
    {
        return commandDAO.getNoResponse(city, startDate, endDate);
    }

    @Override
    public List<PromptCount> getSosCountPerCity(String startDate, String endDate)
    {
        return commandDAO.getSosCountPerCity(startDate, endDate);
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
    public String closePrompt(int id, ClosePrompt closePrompt)
    {
        String result = commandDAO.closePrompt(id, closePrompt);
        dbLoggerDAO.log(AppLog.server(closePrompt.getUsername(), "Closed prompt id %s and result was ref no = %s", id, result));
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
    public String updateSOS(int id, ClosePrompt closePrompt)
    {
        String result = commandDAO.updateSOS(id, closePrompt);
        dbLoggerDAO.log(AppLog.server(closePrompt.getUsername(), "Updated SOS id %s and result was %s", id, result));
        return result;
    }

    @Override
    public List<DistributionList> getNewsFeedsDistributionList()
    {
        return commandDAO.getNewsFeedsDistributionList("default");
    }

    @Override
    public List<DistributionList> getCustomNewsFeedsDistributionList()
    {
        return commandDAO.getNewsFeedsDistributionList("custom");
    }

    @Override
    public List<LovItem> getNewsFeedsDistributionLov(String distributionListCode)
    {
        return commandDAO.getNewsFeedsDistributionLov(distributionListCode);
    }

    @Override
    public String addNewsFeedsCustomGroup(DistributionCustomGroup customGroup)
    {
        String result = commandDAO.addNewsFeedsCustomGroup(customGroup);
        dbLoggerDAO.log(AppLog.server(customGroup.getModifiedBy(), "Created custom newsfeeds group, result was: %s", result));
        return result;
    }

    @Override
    public String editNewsFeedsCustomGroup(DistributionCustomGroup customGroup)
    {
        String result = commandDAO.editNewsFeedsCustomGroup(customGroup);
        dbLoggerDAO.log(AppLog.server(customGroup.getModifiedBy(), "Edited custom newsfeeds group, result was: %s", result));
        return result;
    }

    @Override
    public String deleteNewsFeedsCustomGroup(long id, String deletedBy)
    {
        checkNotNull(emptyToNull(nullToEmpty(deletedBy).trim()), "deletedBy should not be null");
        String result = commandDAO.deleteNewsFeedsCustomGroup(id);
        dbLoggerDAO.log(AppLog.server(deletedBy, "Deleted custom newsfeeds group id %s, result was: %s", id, result));
        return result;
    }

    @Override
    public List<CallTree> listCallTree()
    {
        return commandDAO.listCallTree(null);
    }

    @Override
    public Optional<CallTree> getCallTreeById(long id)
    {
        List<CallTree> list = commandDAO.listCallTree(id);
        if(list.isEmpty())
        {
            return absent();
        }
        return Optional.of(list.get(0));
    }

    @Override
    public long insertCallTree(CallTree callTree)
    {
        long result = commandDAO.insertCallTree(callTree);
        dbLoggerDAO.log(AppLog.server(callTree.getModifiedBy(), "Created call tree with id: %s", result));
        return result;
    }

    @Override
    public String updateCallTree(CallTree callTree)
    {
        String result = commandDAO.updateCallTree(callTree);
        dbLoggerDAO.log(AppLog.server(callTree.getModifiedBy(), "Updated call tree id: %s", callTree.getId()));
        return result;
    }

    @Override
    public String deleteCallTree(long id, String deletedBy)
    {
        checkNotNull(emptyToNull(nullToEmpty(deletedBy).trim()), "deletedBy should not be null");
        String result = commandDAO.deleteCallTree(id);
        dbLoggerDAO.log(AppLog.server(deletedBy, "Deleted call tree id: %s", id));
        return result;
    }
}