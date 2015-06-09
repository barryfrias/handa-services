package handa.core;

import handa.beans.dto.AppLog;


public interface DBLoggerDAO
{
    void insertLog(AppLog applog);
}