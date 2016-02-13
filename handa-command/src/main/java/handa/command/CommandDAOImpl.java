package handa.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
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
import handa.config.HandaCommandConstants.PromptType;
import handa.mappers.CityRowMapper;
import handa.mappers.DistributionListRowMapper;
import handa.mappers.PromptCountRowMapper;
import handa.mappers.SmsInboxRowMapper;
import handa.mappers.SmsOutboxRowMapper;
import handa.procs.ClosePromptProcedure;
import handa.procs.CloseUserReportProcedure;
import handa.procs.DeleteNewsFeedProcedure;
import handa.procs.DeleteSmsProcedure;
import handa.procs.GenericProcedure;
import handa.procs.GetNewsFeedsDistributionLovProcedure;
import handa.procs.GetNewsFeedsProcedure;
import handa.procs.GetNoResponseProcedure;
import handa.procs.GetSmsDistributionLovProcedure;
import handa.procs.GetUserLocAndPromptProcedure;
import handa.procs.GetUserPromptsProcedure;
import handa.procs.GetUserReportsProcedure;
import handa.procs.InsertNewsFeedProcedure;
import handa.procs.NoResponseCountProcedure;
import handa.procs.PromptsCountProcedure;
import handa.procs.ReadSmsInboxProcedure;
import handa.procs.ReportsCountProcedure;
import handa.procs.ResetEventsProcedure;
import handa.procs.SendSmsProcedure;
import handa.procs.UpdateNewsFeedProcedure;
import handa.procs.UsersCountProcedure;

@Component
public class CommandDAOImpl
extends AbstractJdbcDAO
implements CommandDAO
{
    private final PromptsCountProcedure promptsCountProcedure;
    private final InsertNewsFeedProcedure insertNewsFeedProcedure;
    private final UpdateNewsFeedProcedure updateNewsFeedProcedure;
    private final GetNewsFeedsProcedure getNewsFeedsProcedure;
    private final DeleteNewsFeedProcedure deleteNewsFeedProcedure;
    private final GetUserPromptsProcedure getUserPromptsProcedure;
    private final GetUserReportsProcedure getUserReportsProcedure;
    private final ReportsCountProcedure reportsCountProcedure;
    private final GenericProcedure<City> citiesProcedure;
    private final NoResponseCountProcedure noResponseCountProcedure;
    private final GetNoResponseProcedure getNoResponseProcedure;
    private final GenericProcedure<PromptCount> getSosCountPerCityProcedure;
    private final ResetEventsProcedure resetEventsProcedure;
    private final GetUserLocAndPromptProcedure getUsersLocationsProcedure;
    private final ClosePromptProcedure closePromptProcedure;
    private final UsersCountProcedure usersCountProcedure;
    private final CloseUserReportProcedure closeUserReportProcedure;
    private final GenericProcedure<SmsInboxMessage> getSmsInboxProcedure;
    private final ReadSmsInboxProcedure readSmsInboxProcedure;
    private final DeleteSmsProcedure deleteSmsInboxProcedure;
    private final SendSmsProcedure sendSmsProcedure;
    private final GenericProcedure<SmsOutboxMessage> getSmsOutboxProcedure;
    private final DeleteSmsProcedure deleteSmsOutboxProcedure;
    private final GenericProcedure<DistributionList> getSmsDistributionListProcedure;
    private final GetSmsDistributionLovProcedure getSmsDistributionLovProcedure;
    private final GenericProcedure<DistributionList> getNewsFeedsDistributionListProcedure;
    private final GetNewsFeedsDistributionLovProcedure getNewsFeedsDistributionLovProcedure;

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
        this.getSmsDistributionListProcedure = new GenericProcedure<>(dataSource(), "GET_SMS_DISTRIBUTION_LIST", new DistributionListRowMapper());
        this.getNewsFeedsDistributionListProcedure = new GenericProcedure<>(dataSource(), "GET_DISTRIBUTION_LIST", new DistributionListRowMapper());
        this.getNewsFeedsDistributionLovProcedure = new GetNewsFeedsDistributionLovProcedure(dataSource());
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
        return getUserReportsProcedure.list(false, 0);
    }

    @Override
    public List<UserReport> getUserReports(int pageNo)
    {
        return getUserReportsProcedure.list(true, pageNo);
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
    public List<DistributionList> getSmsDistributionList()
    {
        return getSmsDistributionListProcedure.listValues();
    }

    @Override
    public List<LovItem> getSmsDistributionLov(String distributionListCode)
    {
        return getSmsDistributionLovProcedure.list(distributionListCode);
    }

    @Override
    public List<DistributionList> getNewsFeedDistributionList()
    {
        return getNewsFeedsDistributionListProcedure.listValues();
    }

    @Override
    public List<LovItem> getNewsFeedsDistributionLov(String distributionListCode)
    {
        return getNewsFeedsDistributionLovProcedure.list(distributionListCode);
    }
}