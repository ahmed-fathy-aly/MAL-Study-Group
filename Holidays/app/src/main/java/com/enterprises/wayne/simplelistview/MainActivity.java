package com.enterprises.wayne.simplelistview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        String params[] = {};
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
        protected List<String> doInBackground(String... strings)
        {
            // simulate a delay
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                Log.e("Game", "interrupted exception " + e.getMessage());
            }

            // for now, we'll just return random data
            return getRandomData(100);
        }

        @Override
        protected void onPostExecute(List<String> s)
        {
            // dismiss the loading dialog
            progressDialog.dismiss();

            // add the data to the adapter
            mAdapterNumbers.addAll(s);
        }
    }
}
