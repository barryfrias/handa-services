package handa.command;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pldt.itidm.core.utils.AbstractJdbcDAO;

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
import handa.beans.dto.ReadSms;
import handa.beans.dto.SendSms;
import handa.beans.dto.SmsInboxMessage;
import handa.beans.dto.SmsOutboxMessage;
import handa.beans.dto.SosPrompt;
import handa.beans.dto.UserPrompt;
import handa.beans.dto.UserReport;
import handa.config.HandaCommandConstants.PromptType;
import handa.mappers.DashboardFilterRowMapper;
import handa.mappers.SmsOutboxRowMapper;
import handa.procs.AddCmpProcedure;
import handa.procs.AddNewsFeedsCustomGroupProcedure;
import handa.procs.AddSmsCustomGroupProcedure;
import handa.procs.ClosePromptProcedure;
import handa.procs.CloseUserReportProcedure;
import handa.procs.DeleteCallTreeProcedure;
import handa.procs.DeleteCmpProcedure;
import handa.procs.DeleteNewsFeedProcedure;
import handa.procs.DeleteNewsFeedsCustomGroupProcedure;
import handa.procs.DeleteSmsCustomGroupProcedure;
import handa.procs.DeleteSmsProcedure;
import handa.procs.EditCmpProcedure;
import handa.procs.EditNewsFeedsCustomGroupProcedure;
import handa.procs.EditSmsCustomGroupProcedure;
import handa.procs.GenericProcedure;
import handa.procs.GetAllSosProcedure;
import handa.procs.GetNewsFeedsDistributionListProcedure;
import handa.procs.GetNewsFeedsDistributionLovProcedure;
import handa.procs.GetNewsFeedsProcedure;
import handa.procs.GetSmsDistributionListProcedure;
import handa.procs.GetSmsDistributionLovProcedure;
import handa.procs.GetSmsInboxProcedure;
import handa.procs.GetSosCountPerCityProcedure;
import handa.procs.GetUserPromptsProcedure;
import handa.procs.GetUserReportsProcedure;
import handa.procs.InsertCallTreeProcedure;
import handa.procs.InsertNewsFeedProcedure;
import handa.procs.ListCallTreesProcedure;
import handa.procs.ListCmpProcedure;
import handa.procs.ListCmpViewersProcedure;
import handa.procs.PromptsCountProcedure;
import handa.procs.ReadSmsInboxProcedure;
import handa.procs.ReportsCountProcedure;
import handa.procs.ResetEventsProcedure;
import handa.procs.SearchNewsFeedProcedure;
import handa.procs.SendSmsProcedure;
import handa.procs.UpdateCallTreeProcedure;
import handa.procs.UpdateNewsFeedProcedure;
import handa.procs.UpdateSosProcedure;
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
    private final SearchNewsFeedProcedure searchNewsFeedProcedure;
    private final DeleteNewsFeedProcedure deleteNewsFeedProcedure;
    private final GetUserPromptsProcedure getUserPromptsProcedure;
    private final GetUserReportsProcedure getUserReportsProcedure;
    private final ReportsCountProcedure reportsCountProcedure;
    private final GenericProcedure<DashboardFilter> citiesProcedure;
    private final GenericProcedure<DashboardFilter> headsProcedure;
    private final GenericProcedure<DashboardFilter> departmentsProcedure;
    private final GenericProcedure<DashboardFilter> companiesProcedure;
    private final GetSosCountPerCityProcedure getSosCountPerCityProcedure;
    private final ResetEventsProcedure resetEventsProcedure;
    private final ClosePromptProcedure closePromptProcedure;
    private final UsersCountProcedure usersCountProcedure;
    private final CloseUserReportProcedure closeUserReportProcedure;
    private final GetSmsInboxProcedure getSmsInboxProcedure;
    private final ReadSmsInboxProcedure readSmsInboxProcedure;
    private final DeleteSmsProcedure deleteSmsInboxProcedure;
    private final SendSmsProcedure sendSmsProcedure;
    private final GenericProcedure<SmsOutboxMessage> getSmsOutboxProcedure;
    private final DeleteSmsProcedure deleteSmsOutboxProcedure;
    private final GetSmsDistributionListProcedure getSmsDistributionListProcedure;
    private final GetSmsDistributionLovProcedure getSmsDistributionLovProcedure;
    private final GetNewsFeedsDistributionListProcedure getNewsFeedsDistributionListProcedure;
    private final GetNewsFeedsDistributionLovProcedure getNewsFeedsDistributionLovProcedure;
    private final AddNewsFeedsCustomGroupProcedure addNewsFeedsCustomGroupProcedure;
    private final EditNewsFeedsCustomGroupProcedure editNewsFeedsCustomGroupProcedure;
    private final DeleteNewsFeedsCustomGroupProcedure deleteNewsFeedsCustomGroupProcedure;
    private final ListCallTreesProcedure listCallTreesProcedure;
    private final InsertCallTreeProcedure insertCallTreeProcedure;
    private final UpdateCallTreeProcedure updateCallTreeProcedure;
    private final DeleteCallTreeProcedure deleteCallTreeProcedure;
    private final AddSmsCustomGroupProcedure addSmsCustomGroupProcedure;
    private final EditSmsCustomGroupProcedure editSmsCustomGroupProcedure;
    private final DeleteSmsCustomGroupProcedure deleteSmsCustomGroupProcedure;
    private final GetAllSosProcedure getAllSosProcedure;
    private final UpdateSosProcedure updateSosProcedure;
    private final AddCmpProcedure addCmpProcedure;
    private final EditCmpProcedure editCmpProcedure;
    private final DeleteCmpProcedure deleteCmpProcedure;
    private final ListCmpProcedure listCmpProcedure;
    private final ListCmpViewersProcedure listCmpViewersProcedure;

    @Autowired
    public CommandDAOImpl(JdbcTemplate jdbcTemplate)
    {
        super(jdbcTemplate);
        this.promptsCountProcedure = new PromptsCountProcedure(dataSource());
        this.insertNewsFeedProcedure = new InsertNewsFeedProcedure(dataSource());
        this.updateNewsFeedProcedure = new UpdateNewsFeedProcedure(dataSource());
        this.getNewsFeedsProcedure = new GetNewsFeedsProcedure(dataSource());
        this.searchNewsFeedProcedure = new SearchNewsFeedProcedure(dataSource());
        this.deleteNewsFeedProcedure = new DeleteNewsFeedProcedure(dataSource());
        this.getUserPromptsProcedure = new GetUserPromptsProcedure(dataSource());
        this.getUserReportsProcedure = new GetUserReportsProcedure(dataSource());
        this.reportsCountProcedure = new ReportsCountProcedure(dataSource());
        this.resetEventsProcedure = new ResetEventsProcedure(dataSource());
        this.closePromptProcedure = new ClosePromptProcedure(dataSource());
        this.usersCountProcedure = new UsersCountProcedure(dataSource());
        this.closeUserReportProcedure = new CloseUserReportProcedure(dataSource());
        this.readSmsInboxProcedure = new ReadSmsInboxProcedure(dataSource());
        this.sendSmsProcedure = new SendSmsProcedure(dataSource());
        this.getSmsDistributionLovProcedure = new GetSmsDistributionLovProcedure(dataSource());
        this.deleteSmsInboxProcedure = new DeleteSmsProcedure(dataSource(), "DELETE_SMS_INBOX");
        this.deleteSmsOutboxProcedure = new DeleteSmsProcedure(dataSource(), "DELETE_SMS_OUTBOX");
        this.citiesProcedure = new GenericProcedure<>(dataSource(), "GET_CITIES", new DashboardFilterRowMapper());
        this.headsProcedure = new GenericProcedure<>(dataSource(), "dash_get_heads", new DashboardFilterRowMapper());
        this.departmentsProcedure = new GenericProcedure<>(dataSource(), "dash_get_dept", new DashboardFilterRowMapper());
        this.companiesProcedure = new GenericProcedure<>(dataSource(), "dash_get_comp", new DashboardFilterRowMapper());
        this.getSosCountPerCityProcedure = new GetSosCountPerCityProcedure(dataSource());
        this.getSmsInboxProcedure = new GetSmsInboxProcedure(dataSource());
        this.getSmsOutboxProcedure = new GenericProcedure<>(dataSource(), "GET_SMS_OUTBOX", new SmsOutboxRowMapper());
        this.getSmsDistributionListProcedure = new GetSmsDistributionListProcedure(dataSource());
        this.getNewsFeedsDistributionListProcedure = new GetNewsFeedsDistributionListProcedure(dataSource());
        this.getNewsFeedsDistributionLovProcedure = new GetNewsFeedsDistributionLovProcedure(dataSource());
        this.addNewsFeedsCustomGroupProcedure = new AddNewsFeedsCustomGroupProcedure(dataSource());
        this.editNewsFeedsCustomGroupProcedure = new EditNewsFeedsCustomGroupProcedure(dataSource());
        this.deleteNewsFeedsCustomGroupProcedure = new DeleteNewsFeedsCustomGroupProcedure(dataSource());
        this.listCallTreesProcedure = new ListCallTreesProcedure(dataSource());
        this.insertCallTreeProcedure = new InsertCallTreeProcedure(dataSource());
        this.updateCallTreeProcedure = new UpdateCallTreeProcedure(dataSource());
        this.deleteCallTreeProcedure = new DeleteCallTreeProcedure(dataSource());
        this.addSmsCustomGroupProcedure = new AddSmsCustomGroupProcedure(dataSource());
        this.editSmsCustomGroupProcedure = new EditSmsCustomGroupProcedure(dataSource());
        this.deleteSmsCustomGroupProcedure = new DeleteSmsCustomGroupProcedure(dataSource());
        this.getAllSosProcedure = new GetAllSosProcedure(dataSource());
        this.updateSosProcedure = new UpdateSosProcedure(dataSource());
        this.addCmpProcedure = new AddCmpProcedure(dataSource());
        this.editCmpProcedure = new EditCmpProcedure(dataSource());
        this.deleteCmpProcedure = new DeleteCmpProcedure(dataSource());
        this.listCmpProcedure = new ListCmpProcedure(dataSource());
        this.listCmpViewersProcedure = new ListCmpViewersProcedure(dataSource());
    }

    @Override
    public Map<String, Integer> getPromptCount(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        return promptsCountProcedure.getPromptCount(cty, head, dept, comp, startDate, endDate);
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
    public List<NewsFeed> searchNewsFeed(NewsFeedSearch newsFeedSearch)
    {
        return searchNewsFeedProcedure.search(newsFeedSearch);
    }

    @Override
    public int deleteNewsFeed(int id, String deletedBy)
    {
        return deleteNewsFeedProcedure.delete(id, deletedBy);
    }

    @Override
    public List<SosPrompt> getAllSos(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        return getAllSosProcedure.get(cty, head, dept, comp, startDate, endDate);
    }

    @Override
    public List<UserPrompt> getSos(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        return getUserPromptsProcedure.list(PromptType.SOS, cty, head, dept, comp, startDate, endDate);
    }

    @Override
    public List<UserPrompt> getSafe(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        return getUserPromptsProcedure.list(PromptType.SAFE, cty, head, dept, comp, startDate, endDate);
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
    public List<DashboardFilter> getCities()
    {
        return citiesProcedure.listValues();
    }

    @Override
    public List<DashboardFilter> getDashboardHeads()
    {
        return headsProcedure.listValues();
    }

    @Override
    public List<DashboardFilter> getDashboardDepartments()
    {
        return departmentsProcedure.listValues();
    }

    @Override
    public List<DashboardFilter> getDashboardCompanies()
    {
        return companiesProcedure.listValues();
    }

    @Override
    public List<UserPrompt> getNoResponse(String cty, String head, String dept, String comp, String startDate, String endDate)
    {
        return getUserPromptsProcedure.list(PromptType.NR, cty, head, dept, comp, startDate, endDate);
    }

    @Override
    public List<PromptCount> getSosCountPerCity(String startDate, String endDate)
    {
        return getSosCountPerCityProcedure.listValues(startDate, endDate);
    }

    @Override
    public void resetEvents()
    {
        resetEventsProcedure.reset();
    }

    @Override
    public String closePrompt(int id, ClosePrompt closePrompt)
    {
        return closePromptProcedure.closePrompt(id, closePrompt);
    }

    @Override
    public String updateSOS(int id, ClosePrompt closePrompt)
    {
        return updateSosProcedure.update(id, closePrompt);
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
        return getSmsInboxProcedure.list(false);
    }

    @Override
    public List<SmsInboxMessage> getClutterSmsInbox()
    {
        return getSmsInboxProcedure.list(true);
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
    public List<DistributionList> getSmsDistributionList(String type)
    {
        return getSmsDistributionListProcedure.list(type);
    }

    @Override
    public List<LovItem> getSmsDistributionLov(String distributionListCode)
    {
        return getSmsDistributionLovProcedure.list(distributionListCode);
    }

    @Override
    public List<DistributionList> getNewsFeedsDistributionList(String type)
    {
        return getNewsFeedsDistributionListProcedure.list(type);
    }

    @Override
    public String addNewsFeedsCustomGroup(DistributionCustomGroup customGroup)
    {
        return addNewsFeedsCustomGroupProcedure.insert(customGroup);
    }

    @Override
    public String editNewsFeedsCustomGroup(DistributionCustomGroup customGroup)
    {
        return editNewsFeedsCustomGroupProcedure.edit(customGroup);
    }

    @Override
    public String deleteNewsFeedsCustomGroup(long id)
    {
        return deleteNewsFeedsCustomGroupProcedure.delete(id);
    }

    @Override
    public List<LovItem> getNewsFeedsDistributionLov(String distributionListCode)
    {
        return getNewsFeedsDistributionLovProcedure.list(distributionListCode);
    }

    @Override
    public List<CallTree> listCallTree(Long id)
    {
        return listCallTreesProcedure.list(id);
    }

    @Override
    public long insertCallTree(CallTree callTree)
    {
        return insertCallTreeProcedure.save(callTree);
    }

    @Override
    public String updateCallTree(CallTree callTree)
    {
        return updateCallTreeProcedure.update(callTree);
    }

    @Override
    public String deleteCallTree(long id)
    {
        return deleteCallTreeProcedure.delete(id);
    }

    @Override
    public String addSmsCustomGroup(DistributionCustomGroup customGroup)
    {
        return addSmsCustomGroupProcedure.insert(customGroup);
    }

    @Override
    public String editSmsCustomGroup(DistributionCustomGroup customGroup)
    {
        return editSmsCustomGroupProcedure.edit(customGroup);
    }

    @Override
    public String deleteSmsCustomGroup(long id)
    {
        return deleteSmsCustomGroupProcedure.delete(id);
    }

    @Override
    public String addCmp(Cmp cmp)
    {
        return addCmpProcedure.add(cmp);
    }

    @Override
    public String editCmp(Cmp cmp)
    {
        return editCmpProcedure.edit(cmp);
    }

    @Override
    public String deleteCmp(long fileId, String deletedBy)
    {
        return deleteCmpProcedure.delete(fileId, deletedBy);
    }

    @Override
    public List<Cmp> listCmp()
    {
        return listCmpProcedure.list();
    }

    @Override
    public List<CmpViewer> listCmpViewers()
    {
        return listCmpViewersProcedure.list();
    }
}