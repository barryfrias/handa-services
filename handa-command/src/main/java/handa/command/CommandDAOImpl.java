package handa.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itmss.core.utils.AbstractJdbcDAO;

import handa.beans.dto.City;
import handa.beans.dto.ClosePrompt;
import handa.beans.dto.CloseUserReport;
import handa.beans.dto.NewsFeed;
import handa.beans.dto.PromptCount;
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;
import handa.beans.dto.UserLocation;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.command.mappers.CityRowMapper;
import handa.command.mappers.PromptCountRowMapper;
import handa.command.mappers.SmsInboxRowMapper;
import handa.command.mappers.SmsOutboxRowMapper;
import handa.command.procs.ClosePromptProcedure;
import handa.command.procs.CloseUserReportProcedure;
import handa.command.procs.DeleteNewsFeedProcedure;
import handa.command.procs.DeleteSmsProcedure;
import handa.command.procs.GenericProcedure;
import handa.command.procs.GetNewsFeedsProcedure;
import handa.command.procs.GetNoResponseProcedure;
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

    @Autowired
    public CommandDAOImpl(JdbcTemplate jdbcTemplate,
                         @Value("${handa.command.prompts.count.proc}") String promptsCountProcName,
                         @Value("${handa.command.insert.news.feed.proc}") String insertNewsFeedProcName,
                         @Value("${handa.command.update.news.feed.proc}") String updateNewsFeedProcName,
                         @Value("${handa.command.get.news.feed.proc}") String getNewsFeedsProcName,
                         @Value("${handa.command.delete.news.feed.proc}") String deleteNewsFeedProcName,
                         @Value("${handa.command.get.user.prompts.proc}") String getUserPromptsProcName,
                         @Value("${handa.command.get.user.reports.proc}") String getUserReportsProcName,
                         @Value("${handa.command.reports.count.proc}") String reportsCountProcName,
                         @Value("${handa.command.cities.proc}") String citiesProcName,
                         @Value("${handa.command.noresponse.count.proc}") String noResponseCountProcName,
                         @Value("${handa.command.get.noresponse.proc}") String getNoResponseProcName,
                         @Value("${handa.command.sos.countPerCity.proc}") String getSosCountPerCityProcName,
                         @Value("${handa.command.events.reset.proc}") String resetEventsProcName,
                         @Value("${handa.command.get.users.locations.proc}") String getUsersLocationsProcName,
                         @Value("${handa.command.close.prompt.proc}") String closePromptProcName,
                         @Value("${handa.command.users.count.proc}") String usersCountProcName,
                         @Value("${handa.command.close.user.report.proc}") String closeUserReportProcName,
                         @Value("${handa.command.get.sms.inbox.proc}") String getSmsInboxProcName,
                         @Value("${handa.command.read.sms.inbox.proc}") String readSmsInboxProcName,
                         @Value("${handa.command.delete.sms.inbox.proc}") String deleteSmsInboxProcName,
                         @Value("${handa.command.send.sms.proc}") String sendSmsProcName,
                         @Value("${handa.command.get.sms.outbox.proc}") String getSmsOutboxProcName,
                         @Value("${handa.command.delete.sms.outbox.proc}") String deleteSmsOutboxProcName)
    {
        super(jdbcTemplate);
        this.promptsCountProcedure = new PromptsCountProcedure(dataSource(), promptsCountProcName);
        this.insertNewsFeedProcedure = new InsertNewsFeedProcedure(dataSource(), insertNewsFeedProcName);
        this.updateNewsFeedProcedure = new UpdateNewsFeedProcedure(dataSource(), updateNewsFeedProcName);
        this.getNewsFeedsProcedure = new GetNewsFeedsProcedure(dataSource(), getNewsFeedsProcName);
        this.deleteNewsFeedProcedure = new DeleteNewsFeedProcedure(dataSource(), deleteNewsFeedProcName);
        this.getUserPromptsProcedure = new GetUserPromptsProcedure(dataSource(), getUserPromptsProcName);
        this.getUserReportsProcedure = new GetUserReportsProcedure(dataSource(), getUserReportsProcName);
        this.reportsCountProcedure = new ReportsCountProcedure(dataSource(), reportsCountProcName);
        this.citiesProcedure = new GenericProcedure<>(dataSource(), citiesProcName, new CityRowMapper());
        this.noResponseCountProcedure = new NoResponseCountProcedure(dataSource(), noResponseCountProcName);
        this.getNoResponseProcedure = new GetNoResponseProcedure(dataSource(), getNoResponseProcName);
        this.getSosCountPerCityProcedure = new GenericProcedure<>(dataSource(), getSosCountPerCityProcName, new PromptCountRowMapper());
        this.resetEventsProcedure = new ResetEventsProcedure(dataSource(), resetEventsProcName);
        this.getUsersLocationsProcedure = new GetUserLocAndPromptProcedure(dataSource(), getUsersLocationsProcName);
        this.closePromptProcedure = new ClosePromptProcedure(dataSource(), closePromptProcName);
        this.usersCountProcedure = new UsersCountProcedure(dataSource(), usersCountProcName);
        this.closeUserReportProcedure = new CloseUserReportProcedure(dataSource(), closeUserReportProcName);
        this.getSmsInboxProcedure = new GenericProcedure<>(dataSource(), getSmsInboxProcName, new SmsInboxRowMapper());
        this.readSmsInboxProcedure = new ReadSmsInboxProcedure(dataSource(), readSmsInboxProcName);
        this.deleteSmsInboxProcedure = new DeleteSmsProcedure(dataSource(), deleteSmsInboxProcName);
        this.sendSmsProcedure = new SendSmsProcedure(dataSource(), sendSmsProcName);
        this.getSmsOutboxProcedure = new GenericProcedure<>(dataSource(), getSmsOutboxProcName, new SmsOutboxRowMapper());
        this.deleteSmsOutboxProcedure = new DeleteSmsProcedure(dataSource(), deleteSmsOutboxProcName);
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
        return getNewsFeedsProcedure.list();
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
}