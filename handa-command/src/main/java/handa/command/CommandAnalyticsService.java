package handa.command;

import handa.beans.dto.EventReport;
import handa.beans.dto.ReportInput;

public interface CommandAnalyticsService
{
    EventReport byEvent(ReportInput reportInput);
}