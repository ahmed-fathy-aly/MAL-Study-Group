package com.enterprises.wayne.simplelistview;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by ahmed on 7/18/2016.
 */
public class Holiday
{
    /* fields */
    private String name;
    private Calendar date;
    private boolean isPublic;

    /* constructor */
    public Holiday(String name, Calendar date, boolean isPublic)
    {
        this.name = name;
        this.date = date;
        this.isPublic = isPublic;
    }

    /* getters */
    public String getName()
    {
        return name;
    }

    public Calendar getDate()
    {
        return date;
    }

    public boolean isPublic()
    {
        return isPublic;
    }

    /* methods */

    /**
     * parses a holiday object from a json
     * @return null if parsing failed
     */
    public static Holiday fromJson(JSONObject jsonObject)
    {
        try
        {
            // parse the fields
            String name = jsonObject.getString("name");
            boolean isPublic = jsonObject.getBoolean("public");
            String dateStr = jsonObject.getString("date");
            Calendar calendar = DateUtils.parseCalendar(dateStr);
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
