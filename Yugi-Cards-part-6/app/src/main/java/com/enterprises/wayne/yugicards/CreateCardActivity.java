package com.enterprises.wayne.yugicards;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * fills the form to create a card
 */
public class CreateCardActivity extends AppCompatActivity
{


    /**
     * only use this to launch this activity
     */
    public static Intent getIntent(Context context)
    {
        return new Intent(context, CreateCardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        if (getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CreateCardFragment.newInstance())
                    .commit();
    }

}
