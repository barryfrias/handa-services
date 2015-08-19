package handa.command;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;
import static handa.config.HandaCommandConstants.NA;
import static handa.config.HandaCommandConstants.OK;
import handa.beans.dto.AppLog;
import handa.beans.dto.AppLog.Source;
import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SmsMessage;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.core.DBLoggerDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandServiceImpl
implements CommandService
{
    final static Logger log = LoggerFactory.getLogger(CommandServiceImpl.class);

    @Value("${handa.command.upload.directory}")
    private String uploadDirectory;

    private CommandDAO commandDAO;
    private DBLoggerDAO dbLoggerDAO;

    @Autowired
    public CommandServiceImpl(CommandDAO commandDAO, DBLoggerDAO dbLoggerDAO)
    {
        this.commandDAO = commandDAO;
        this.dbLoggerDAO = dbLoggerDAO;
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
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, newsFeed.getUsername(), NA,
                                         String.format("Posted news feed entry id %s", feed.getId())));
        return feed;
    }

    @Override
    public NewsFeed updateNewsFeed(NewsFeed newsFeed)
    {
        NewsFeed feed = commandDAO.updateNewsFeed(newsFeed);
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, newsFeed.getUsername(), NA,
                                         String.format("Updated news feed entry id %s", feed.getId())));
        return feed;
    }

    @Override
    public List<NewsFeed> getNewsFeeds()
    {
        return commandDAO.getNewsFeeds();
    }

    @Override
    public int deleteNewsFeed(int id, String deletedBy)
    {
        int result = commandDAO.deleteNewsFeed(id, deletedBy);
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, deletedBy, NA,
                                         String.format("Deleted news feed id %s and result was %s", id, result)));
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
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, resetBy, NA,
                                         String.format("Events was reset by %s", resetBy)));
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
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, closePrompt.getUsername(), NA,
                              String.format("Closed prompt id %s and result was %s", id, result)));
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
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, closeUserReport.getUsername(), NA,
                              String.format("Closed user report id %s and result was %s", id, result)));
        return result;
    }

    @Override
    public List<SmsMessage> getSms()
    {
        return commandDAO.getSms();
    }

    @Override
    public int readSms(int id, ReadSms readSms)
    {
        int result = commandDAO.readSms(id, readSms);
        dbLoggerDAO.insertLog(new AppLog(Source.SERVER, readSms.getReadBy(), NA,
                              String.format("Marked sms id %s as read and result was %s", id, result)));
        return result;
    }
}