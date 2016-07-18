package com.enterprises.wayne.simplelistview;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ahmed on 7/18/2016.
 */
public class ParsingUtils
{
    /**
     * parses a date like "2014-07-11" (year-month-day)
     *
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

    /**
     * parses a holiday object from a json
     *
     * @return null if parsing failed
     */
    public static Holiday parseHoliday(JSONObject jsonObject)
    {
        try
        {
            // parse the fields
            String name = jsonObject.getString("name");
            boolean isPublic = jsonObject.getBoolean("public");
            String dateStr = jsonObject.getString("date");
            Calendar calendar = parseCalendar(dateStr);
            if (calendar == null)
                return null;

            // create the object
            return new Holiday(name, calendar, isPublic);

        } catch (JSONException e)
        {
            Log.e("Game", "error parsing holiday " + e.getMessage());
            return null;
        }

    }
}
