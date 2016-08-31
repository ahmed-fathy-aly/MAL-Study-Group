package com.enterprises.wayne.yugicards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity
{

    /* constants */
    public static final String EXTRAS_CARD = "extrasCard";
    public static final String LOG_TAG = DetailsActivity.class.getSimpleName();


    /* fields */
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setHomeButtonEnabled(true);

        // get data from the intent
        if (getIntent() != null && getIntent().hasExtra(EXTRAS_CARD))
            card = (Card) getIntent().getSerializableExtra(EXTRAS_CARD);

        // add the fragment
        DetailsFragment detailsFragment = DetailsFragment.newInstance(card);
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .commit();
    }

}
