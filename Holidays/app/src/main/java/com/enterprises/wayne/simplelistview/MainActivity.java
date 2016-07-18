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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
     * launches an AsyncTask that downlods the data from the server and puts it in the list
     */
    private void loadData()
    {
        HolidaysLoaderTask holidaysLoaderTask = new HolidaysLoaderTask();
        String params[] = {"US", "2016"};
        holidaysLoaderTask.execute(params);
    }

    /**
     * fills an array of the specified size with random numbers from 1 to 1000
     */
    private List<String> getRandomData(int size)
    {
        Random random = new Random();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < size; i++)
            result.add((random.nextInt(1000) + 1) + "");
        return result;
    }

    /**
     * downloads a list of holidays from the backend
     * doInBackground returns null in case of an error
     */
    private class HolidaysLoaderTask extends AsyncTask<String, Void, List<String>>
    {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            // show a dialog while we are loading the data
            progressDialog = ProgressDialog.show(MainActivity.this, "", getString(R.string.loading));
        }

        @Override
        protected List<String> doInBackground(String... params)
        {
            // make the connection
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultString;

            try
            {
                // construct the url
                Uri builtUri = Uri.parse("https://holidayapi.com/v1/holidays").buildUpon()
                        .appendQueryParameter("country", params[0])
                        .appendQueryParameter("year", params[1])
                        .build();
                URL url = new URL(builtUri.toString());
                Log.d("Game", "url = " + builtUri.toString());

                // make the connection to the server
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // read the input stream to a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                    buffer.append(line + "\n");

                if (buffer.length() == 0)
                    return null;
                resultString = buffer.toString();

                Log.d("Game", "result string: " + resultString);

            } catch (IOException e)
            {
                Log.e("Game", "Error ", e);
                return null;
            } finally
            {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null)
                    try
                    {
                        reader.close();
                    } catch (final IOException e)
                    {
                        Log.e("Game", "Error closing stream", e);
                    }
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<String> s)
        {
            // dismiss the loading dialog
            progressDialog.dismiss();

            // add the data to the adapter
            if (s != null)
                mAdapterNumbers.addAll(s);
        }
    }
}
