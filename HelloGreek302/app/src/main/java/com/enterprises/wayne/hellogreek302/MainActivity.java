package com.enterprises.wayne.hellogreek302;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * here's where the java code for the activity is, it contains its logic
 */
public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // call the constructor of the super class (AppCompatActivity -> ... -> Activity -> ... -> Context -> Object
        super.onCreate(savedInstanceState);

        // inflate the layout from xml and set it to be the content
        setContentView(R.layout.activity_main);
    }
}
