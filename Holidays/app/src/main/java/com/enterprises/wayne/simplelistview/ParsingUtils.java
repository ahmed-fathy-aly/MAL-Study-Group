package com.enterprises.wayne.simplelistview;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * parses a list of holiday objects from a json
     * sorts the holidays by their date, earliest first
     *
     * @return null if parsing failed
     */
    static List<Holiday> parseHolidayList(String resultString)
    {
        try
        {
            // check status
            JSONObject jsonObject = new JSONObject(resultString);
            int status = jsonObject.getInt("status");
            if (status != 200)
                return null;

            // parse the holidays objects
            List<Holiday> holidayList = new LinkedList<>();
            JSONObject holidaysJsonObject = jsonObject.getJSONObject("holidays");
            Iterator<String> iterator = holidaysJsonObject.keys();
            while (iterator.hasNext())
            {
                String key = iterator.next();
                JSONArray holidaysArr = holidaysJsonObject.getJSONArray(key);
                for (int i = 0; i < holidaysArr.length(); i++)
                    holidayList.add(ParsingUtils.parseHoliday(holidaysArr.getJSONObject(i)));
            }

            // sort by date
            Collections.sort(holidayList, new Comparator<Holiday>()
            {
                @Override
                public int compare(Holiday lhs, Holiday rhs)
                {
                    return lhs.getDate().compareTo(rhs.getDate());
                }
            });
            return holidayList;

        } catch (JSONException e)
        {
            Log.e("Game", "error parsing list of holidays " + e.getMessage());
            return null;
        }

    }
}
