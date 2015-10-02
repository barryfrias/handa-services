package handa.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.LovItem;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsDistributionList;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.command.mappers.CityRowMapper;
import handa.command.mappers.PromptCountRowMapper;
import handa.command.mappers.SmsDistributionListRowMapper;
import handa.command.mappers.SmsInboxRowMapper;
import handa.command.mappers.SmsOutboxRowMapper;
import handa.command.procs.ClosePromptProcedure;
import handa.command.procs.CloseUserReportProcedure;
import handa.command.procs.DeleteNewsFeedProcedure;
import handa.command.procs.DeleteSmsProcedure;
import handa.command.procs.GenericProcedure;
import handa.command.procs.GetNewsFeedsProcedure;
import handa.command.procs.GetNoResponseProcedure;
import handa.command.procs.GetSmsDistributionLovProcedure;
import handa.command.procs.GetUserLocAndPromptProcedure;
import handa.command.procs.GetUserPromptsProcedure;
import handa.command.procs.GetUserReportsProcedure;
import handa.command.procs.InsertNewsFeedProcedure;
import handa.command.procs.NoResponseCountProcedure;
import handa.command.procs.PromptsCountProcedure;
import handa.command.procs.ReadSmsInboxProcedure;
import handa.command.procs.ReportsCountProcedure;
import handa.command.procs.ResetEventsProcedure;
import handa.command.procs.SendSmsProcedure;
import handa.command.procs.UpdateNewsFeedProcedure;
import handa.command.procs.UsersCountProcedure;
import handa.config.HandaCommandConstants.PromptType;

@Component
public class CommandDAOImpl
extends AbstractJdbcDAO
implements CommandDAO
{
    private PromptsCountProcedure promptsCountProcedure;
    private InsertNewsFeedProcedure insertNewsFeedProcedure;
    private UpdateNewsFeedProcedure updateNewsFeedProcedure;
    private GetNewsFeedsProcedure getNewsFeedsProcedure;
    private DeleteNewsFeedProcedure deleteNewsFeedProcedure;
    private GetUserPromptsProcedure getUserPromptsProcedure;
    private GetUserReportsProcedure getUserReportsProcedure;
    private ReportsCountProcedure reportsCountProcedure;
    private GenericProcedure<City> citiesProcedure;
    private NoResponseCountProcedure noResponseCountProcedure;
    private GetNoResponseProcedure getNoResponseProcedure;
    private GenericProcedure<PromptCount> getSosCountPerCityProcedure;
    private ResetEventsProcedure resetEventsProcedure;
    private GetUserLocAndPromptProcedure getUsersLocationsProcedure;
    private ClosePromptProcedure closePromptProcedure;
    private UsersCountProcedure usersCountProcedure;
    private CloseUserReportProcedure closeUserReportProcedure;
    private GenericProcedure<SmsInboxMessage> getSmsInboxProcedure;
    private ReadSmsInboxProcedure readSmsInboxProcedure;
    private DeleteSmsProcedure deleteSmsInboxProcedure;
    private SendSmsProcedure sendSmsProcedure;
    private GenericProcedure<SmsOutboxMessage> getSmsOutboxProcedure;
    private DeleteSmsProcedure deleteSmsOutboxProcedure;
    private GenericProcedure<SmsDistributionList> getSmsDistributionListProcedure;
    private GetSmsDistributionLovProcedure getSmsDistributionLovProcedure;

    @Autowired
    public CommandDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.promptsCountProcedure = new PromptsCountProcedure(dataSource());
        this.insertNewsFeedProcedure = new InsertNewsFeedProcedure(dataSource());
        this.updateNewsFeedProcedure = new UpdateNewsFeedProcedure(dataSource());
        this.getNewsFeedsProcedure = new GetNewsFeedsProcedure(dataSource());
        this.deleteNewsFeedProcedure = new DeleteNewsFeedProcedure(dataSource());
        this.getUserPromptsProcedure = new GetUserPromptsProcedure(dataSource());
        this.getUserReportsProcedure = new GetUserReportsProcedure(dataSource());
        this.reportsCountProcedure = new ReportsCountProcedure(dataSource());
        this.noResponseCountProcedure = new NoResponseCountProcedure(dataSource());
        this.getNoResponseProcedure = new GetNoResponseProcedure(dataSource());
        this.resetEventsProcedure = new ResetEventsProcedure(dataSource());
        this.getUsersLocationsProcedure = new GetUserLocAndPromptProcedure(dataSource());
        this.closePromptProcedure = new ClosePromptProcedure(dataSource());
        this.usersCountProcedure = new UsersCountProcedure(dataSource());
        this.closeUserReportProcedure = new CloseUserReportProcedure(dataSource());
        this.readSmsInboxProcedure = new ReadSmsInboxProcedure(dataSource());
        this.sendSmsProcedure = new SendSmsProcedure(dataSource());
        this.getSmsDistributionLovProcedure = new GetSmsDistributionLovProcedure(dataSource());
        this.deleteSmsInboxProcedure = new DeleteSmsProcedure(dataSource(), "DELETE_SMS_INBOX");
        this.deleteSmsOutboxProcedure = new DeleteSmsProcedure(dataSource(), "DELETE_SMS_OUTBOX");
        this.citiesProcedure = new GenericProcedure<>(dataSource(), "GET_CITIES", new CityRowMapper());
        this.getSosCountPerCityProcedure = new GenericProcedure<>(dataSource(), "GET_SOS_COUNT_PER_CITY", new PromptCountRowMapper());
        this.getSmsInboxProcedure = new GenericProcedure<>(dataSource(), "GET_SMS_INBOX", new SmsInboxRowMapper());
        this.getSmsOutboxProcedure = new GenericProcedure<>(dataSource(), "GET_SMS_OUTBOX", new SmsOutboxRowMapper());
        this.getSmsDistributionListProcedure = new GenericProcedure<>(dataSource(), "GET_SMS_DISTRIBUTION_LIST", new SmsDistributionListRowMapper());
    }

    @Override
    public int getSosCount(String city)
    {
        return promptsCountProcedure.getPromptCount(PromptType.SOS, city);
    }

    @Override
    public int getSafeCount(String city)
    {
        return promptsCountProcedure.getPromptCount(PromptType.SAFE, city);
    }

    @Override
    public NewsFeed postNewsFeed(NewsFeed newsFeed)
    {
        return insertNewsFeedProcedure.insert(newsFeed);
    }

    @Override
    public NewsFeed updateNewsFeed(NewsFeed newsFeed)
    {
        return updateNewsFeedProcedure.update(newsFeed);
    }

    @Override
    public List<NewsFeed> getNewsFeeds()
    {
        return getNewsFeedsProcedure.list(false, 0);
    }

    @Override
    public List<NewsFeed> getNewsFeeds(int pageNo)
    {
        return getNewsFeedsProcedure.list(true, pageNo);
    }

    @Override
    public int deleteNewsFeed(int id, String deletedBy)
    {
        return deleteNewsFeedProcedure.delete(id, deletedBy);
    }

    @Override
    public List<UserPrompt> getSos(String city)
    {
        return getUserPromptsProcedure.list(PromptType.SOS, city);
    }

    @Override
    public List<UserPrompt> getSafe(String city)
    {
        return getUserPromptsProcedure.list(PromptType.SAFE, city);
    }

    @Override
    public List<UserReport> getUserReports()
    {
        return getUserReportsProcedure.list();
    }

    @Override
    public int getReportsCount()
    {
        return reportsCountProcedure.getReportsCount();
    }

    @Override
    public List<City> getCities()
    {
        return citiesProcedure.listValues();
    }

    @Override
    public int getNoResponseCount(String city)
    {
        return noResponseCountProcedure.getCount(city);
    }

    @Override
    public List<UserPrompt> getNoResponse(String city)
    {
        return getNoResponseProcedure.list(city);
    }

    @Override
    public List<PromptCount> getSosCountPerCity()
    {
        return getSosCountPerCityProcedure.listValues();
    }

    @Override
    public void resetEvents()
    {
        resetEventsProcedure.reset();
    }

    @Override
    public List<UserLocation> getUsersLocations(String city)
    {
        return getUsersLocationsProcedure.list(city);
    }

    @Override
    public int closePrompt(int id, ClosePrompt closePrompt)
    {
        return closePromptProcedure.closePrompt(id, closePrompt);
    }

    @Override
    public int getUsersCount(String city)
    {
        return usersCountProcedure.count(city);
    }

    @Override
    public int closeUserReport(int id, CloseUserReport closeUserReport)
    {
        return closeUserReportProcedure.closeUserReport(id, closeUserReport);
    }

    @Override
    public List<SmsInboxMessage> getSmsInbox()
    {
        return getSmsInboxProcedure.listValues();
    }

    @Override
    public int readSms(int id, ReadSms readSms)
    {
        return readSmsInboxProcedure.markAsRead(id, readSms);
    }

    @Override
    public int deleteSms(int id, String deletedBy)
    {
        return deleteSmsInboxProcedure.delete(id, deletedBy);
    }

    @Override
    public String sendSms(SendSms sendSms)
    {
        return sendSmsProcedure.send(sendSms);
    }

    @Override
    public List<SmsOutboxMessage> getSmsOutbox()
    {
        return getSmsOutboxProcedure.listValues();
    }

    @Override
    public int deleteSmsOutbox(int id, String deletedBy)
    {
        return deleteSmsOutboxProcedure.delete(id, deletedBy);
    }

    @Override
    public List<SmsDistributionList> getSmsDistributionList()
    {
        return getSmsDistributionListProcedure.listValues();
    }

    @Override
    public List<LovItem> getSmsDistributionLov(String distributionListCode)
    {
        return getSmsDistributionLovProcedure.list(distributionListCode);
    }
}