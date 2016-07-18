package com.enterprises.wayne.simplelistview;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ahmed on 7/18/2016.
 */
public class DateUtils
{
    /**
     * parses a date like "2014-07-11" (year-month-day)
     * @return null if the format is wrong
     */
    public static Calendar parseCalendar(String dateStr)
    {
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse(dateStr);
            return simpleDateFormat.getCalendar();
        } catch (ParseException e)
        {
            Log.e("Game", "error parsing calendar");
            return null;
        }

    }
}
