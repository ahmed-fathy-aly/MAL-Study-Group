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




}
