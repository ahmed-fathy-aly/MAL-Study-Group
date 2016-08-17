package com.enterprises.wayne.yugicards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
{
    /* constants */
    public final String LOG_TAG = MainActivity.class.getSimpleName();

    /* UI */
    ListView listViewCards;
    ArrayAdapter<String> adapterCards;
    SwipeRefreshLayout swipeRefreshLayout;

    /* fields */
    List<Card> cardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference the views
        listViewCards = (ListView) findViewById(R.id.list_view_cards);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // create an adapter with empty data
        ArrayList<String> emptyData = new ArrayList<>();
        adapterCards = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                emptyData
        );

        // bind the adapter to the list view
        listViewCards.setAdapter(adapterCards);

        // add listener
        listViewCards.setOnItemClickListener(this);

        // setup swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_item_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        loadDataFromDatabase();
    }


    @Override
    public void onRefresh()
    {
        loadDataFromAPI();
    }

    /**
     * loads the cards from the local database
     */
    private void loadDataFromDatabase()
    {
        // get the card type
        String cardType = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.key_pref_card_type), getString(R.string.monster));
        cardType = Character.toUpperCase(cardType.charAt(0)) + cardType.substring(1);

        // query the local database
        Cursor cursor = getContentResolver()
                .query(CardContract.CardEntry.CONTENT_URI,
                        null,
                        CardContract.CardEntry.COLOUMN_TYPE + "=?",
                        new String[]{cardType},
                        null,
                        null);

        // parse data from the cursor
        cardsList = new ArrayList<>();
        if (cursor.moveToFirst())
            do
            {
                Card card = new Card();

                card.setTitle(cursor.getString(cursor.getColumnIndex(CardContract.CardEntry._ID)));
                card.setDescription(cursor.getString(cursor.getColumnIndex(CardContract.CardEntry.COLOUMN_DESCRIPTION)));
                card.setType(cursor.getString(cursor.getColumnIndex(CardContract.CardEntry.COLOUMN_TYPE)));
                card.setImageUrl(cursor.getString(cursor.getColumnIndex(CardContract.CardEntry.COLOUMN_IMAGE_URL)));

                cardsList.add(card);
            } while (cursor.moveToNext());

        // convert to a list of strings
        List<String> cardsString = new ArrayList<String>();
        for (Card card : cardsList)
            cardsString.add(card.getTitle());
        Log.d(LOG_TAG, "database cards " + cardsList);

        // add to the adapter
        adapterCards.clear();
        adapterCards.addAll(cardsString);
    }

    /**
     * downloads the cards data from the backend API
     */
    private void loadDataFromAPI()
    {
        // make a GET requests
        String cardType = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.key_pref_card_type), getString(R.string.monster));
        String url = "https://greek-302.herokuapp.com/cards/" + cardType;
        Ion.with(this)
                .load("GET", url)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {

                        // stop the swipe to refresh
                        swipeRefreshLayout.setRefreshing(false);

                        // check error
                        if (e != null)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // parse the data
                        MainActivity.this.cardsList = ParsingUtils.parseResponse(result);
                        if (cardsList == null)
                        {
                            Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // convert to a list of strings
                        List<String> cardsString = new ArrayList<String>();
                        for (Card card : cardsList)
                            cardsString.add(card.getTitle());

                        // add to the adapter
                        adapterCards.clear();
                        adapterCards.addAll(cardsString);

                        // save to the local database
                        for (Card card : cardsList)
                        {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(CardContract.CardEntry._ID, card.getTitle());
                            contentValues.put(CardContract.CardEntry.COLOUMN_DESCRIPTION, card.getDescription());
                            contentValues.put(CardContract.CardEntry.COLOUMN_TYPE, card.getType());
                            contentValues.put(CardContract.CardEntry.COLOUMN_IMAGE_URL, card.getImageUrl());
                            getContentResolver()
                                    .insert(CardContract.CardEntry.CONTENT_URI, contentValues);
                        }

                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        // open the details activity
        Intent intent = new Intent(this, DetailsActivity.class);
        Card card = cardsList.get(position);
        intent.putExtra(DetailsActivity.EXTRAS_CARD, card);
        startActivity(intent);
    }

}
