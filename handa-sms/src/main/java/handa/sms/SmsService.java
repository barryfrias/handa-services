package handa.sms;

import handa.beans.dto.ClosePrompt;
import handa.beans.dto.PromptCount;

import java.util.List;


public interface SmsService
{
    List<PromptCount> getSosCountPerCity();
    int closePrompt(int id, ClosePrompt closePrompt);
}