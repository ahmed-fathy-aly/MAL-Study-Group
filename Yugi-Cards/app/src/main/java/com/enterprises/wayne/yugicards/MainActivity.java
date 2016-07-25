package com.enterprises.wayne.yugicards;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    /* constants */
    public final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }

    /**
     * downloads the cards data from the backend API
     */
    private void loadData()
    {
        // show a progress dialog
        final ProgressDialog progressDialog = ProgressDialog.show(this, "", getString(R.string.loading));

        // make a GET requests
        String url = "https://greek-302.herokuapp.com/cards/monster";
        Ion.with(this)
                .load("GET", url)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        // dismiss the dialog
                        progressDialog.dismiss();

                        // check error
                        if (e != null)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // parse the data
                        List<Card> cards = ParsingUtils.parseResponse(result);
                    }
                });
    }
}
