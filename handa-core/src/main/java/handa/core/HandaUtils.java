package handa.core;

import java.text.SimpleDateFormat;

public class HandaUtils
{
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    static { df.setLenient(false); }

    public static boolean checkDate(String date)
    {
        if(null == date || date.length() != 8) return false;
        try
        {
            Integer.valueOf(date);
            df.parse(date);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}