package com.enterprises.wayne.yugicards.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.enterprises.wayne.yugicards.R;

public class SettingsActivity extends PreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
