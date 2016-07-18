package com.enterprises.wayne.simplelistview;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private ListView mListViewNumbers;
    private ArrayAdapter<String> mAdapterNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the adapter
        mAdapterNumbers = new ArrayAdapter<String>(
                this
                , R.layout.row_number
                , R.id.text_view_number
                , new ArrayList<String>()
        );

        // reference the list view
        mListViewNumbers = (ListView) findViewById(R.id.list_view_numbers);

        // bind the adapter to the list view
        mListViewNumbers.setAdapter(mAdapterNumbers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // check which button was clicked
        if (item.getItemId() == R.id.menu_item_refresh)
        {
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * makes a GET request to get the date from the web service
     */
    private void loadData()
    {
        // construct the url
        Uri builtUri = Uri.parse("https://holidayapi.com/v1/holidays").buildUpon()
                .appendQueryParameter("country", "US")
                .appendQueryParameter("year", "2016")
                .build();
        String url = builtUri.toString();


        // make the request
        final ProgressDialog progressDialog = ProgressDialog.show(this, "", getString(R.string.loading));
        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String jsonString)
                    {
                        // dismiss the dialog
                        progressDialog.dismiss();

                        // check error
                        if (e != null)
                        {
                            Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // parse the data
                        List<Holiday> holidayList = ParsingUtils.parseHolidayList(jsonString);
                        if (holidayList == null)
                        {
                            Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // convert to a list of string
                        List<String> result = new ArrayList<>();
                        for (Holiday holiday : holidayList)
                            result.add(holiday.getName());
                        mAdapterNumbers.addAll(result);
                    }
                });

    }


}
