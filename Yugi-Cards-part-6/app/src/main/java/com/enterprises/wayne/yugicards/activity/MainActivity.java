package com.enterprises.wayne.yugicards.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.enterprises.wayne.yugicards.utils.ParsingUtils;
import com.enterprises.wayne.yugicards.R;
import com.enterprises.wayne.yugicards.database.CardContract;
import com.enterprises.wayne.yugicards.event.CardAddedEvent;
import com.enterprises.wayne.yugicards.fragment.CreateCardFragment;
import com.enterprises.wayne.yugicards.fragment.DetailsFragment;
import com.enterprises.wayne.yugicards.entity.Card;
import com.enterprises.wayne.yugicards.ui.CardsAdapter;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, CardsAdapter.Listener
{

    /* constants */
    private static final int DATABASE_SAVE_LOADER = 42;
    private static final int DATABASE_LOAD_LOADER = 4242;
    public final String LOG_TAG = MainActivity.class.getSimpleName();

    /* UI */
    private RecyclerView mRecyclerViewCards;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CardsAdapter mCardsAdapter;

    /* fields */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference the views
        mRecyclerViewCards = (RecyclerView) findViewById(R.id.recycler_view_cards);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // check if it has enough space for two panes
        mTwoPane = findViewById(R.id.container_details_fragment) != null;

        // setup recycler view
        mCardsAdapter = new CardsAdapter(this);
        mCardsAdapter.setListenr(this);
        mRecyclerViewCards.setAdapter(mCardsAdapter);
        mRecyclerViewCards.setLayoutManager(new GridLayoutManager(this, 2));

        // setup swipe to refresh
        mSwipeRefreshLayout.setOnRefreshListener(this);

        EventBus.getDefault().register(this);

        loadDataFromDatabase();

        Log.d("Game", "onCreate");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        } else if (item.getItemId() == R.id.menu_item_create_card)
        {
            if (findViewById(R.id.container_details_fragment) != null)
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_details_fragment, CreateCardFragment.newInstance())
                        .commit();
            else
                startActivity(CreateCardActivity.getIntent(this));

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh()
    {
        loadDataFromAPI();
    }

    /**
     * loads the cards from the local database using a loader
     */
    private void loadDataFromDatabase()
    {
        // get the card type
        String cardType = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.key_pref_card_type), getString(R.string.monster));
        cardType = Character.toUpperCase(cardType.charAt(0)) + cardType.substring(1);
        final String finalCardType = cardType;

        Log.d("Game", "load from database");
        LoaderManager.LoaderCallbacks<List<Card>> callbacks = new LoaderManager.LoaderCallbacks<List<Card>>()
        {
            @Override
            public Loader<List<Card>> onCreateLoader(int id, Bundle args)
            {
                return new DatabaseLoadLoader(MainActivity.this, finalCardType);
            }

            @Override
            public void onLoadFinished(Loader<List<Card>> loader, List<Card> cards)
            {

                // update the adapter
                mCardsAdapter.setData(cards);
                Log.d("Game", "loaded " + cards.size());
            }

            @Override
            public void onLoaderReset(Loader<List<Card>> loader)
            {

            }
        };
        Loader<List<Card>> loader = getSupportLoaderManager().initLoader(DATABASE_LOAD_LOADER, null, callbacks);
        loader.forceLoad();
    }

    /**
     * saves to a local database using a loader
     */

    public void saveDataToDatabase(final List<Card> cardsList)
    {
        LoaderManager.LoaderCallbacks<Void> callbacks = new LoaderManager.LoaderCallbacks<Void>()
        {

            @Override
            public Loader<Void> onCreateLoader(int id, Bundle args)
            {
                return new DatabaseSaveLoader(MainActivity.this, cardsList);
            }

            @Override
            public void onLoadFinished(Loader<Void> loader, Void data)
            {

            }

            @Override
            public void onLoaderReset(Loader<Void> loader)
            {

            }
        };
        Loader<Void> loader = getSupportLoaderManager().initLoader(DATABASE_SAVE_LOADER, null, callbacks);
        loader.forceLoad();
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
                        mSwipeRefreshLayout.setRefreshing(false);

                        // check error
                        if (e != null)
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // parse the data
                        final List<Card> cardsList = ParsingUtils.parseResponse(result);
                        if (cardsList == null)
                        {
                            Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // save to the local database
                        saveDataToDatabase(cardsList);

                        // now load from the database
                        loadDataFromDatabase();
                    }
                });
    }

    @Override
    public void onCardClicked(Card card)
    {
        if (mTwoPane)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_details_fragment, DetailsFragment.newInstance(card))
                    .commit();
        } else
        {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRAS_CARD, card);
            startActivity(intent);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CardAddedEvent event)
    {
        // check it's the same type as those displayed
        String cardType = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.key_pref_card_type), getString(R.string.monster));

        if (event.card.getType().toLowerCase().equals(cardType.toLowerCase()))
            mCardsAdapter.add(event.card);
    }

    /**
     * loads a list of cards from the database
     */
    static class DatabaseLoadLoader extends AsyncTaskLoader<List<Card>>
    {
        private String mCardType;

        public DatabaseLoadLoader(Context context, String type)
        {
            super(context);
            this.mCardType = type;
        }

        @Override
        public List<Card> loadInBackground()
        {
            Log.d("Game", "load in background");
            // query the local database
            Cursor cursor = getContext()
                    .getContentResolver()
                    .query(CardContract.CardEntry.CONTENT_URI,
                            null,
                            CardContract.CardEntry.COLOUMN_TYPE + "=?",
                            new String[]{mCardType},
                            null,
                            null);

            // parse data from the cursor
            List<Card> cardsList = new ArrayList<>();
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


            Log.d("Game", "loaded in background " + cardsList.size());
            return cardsList;
        }

    }

    /**
     * saves a list of cards to the database
     */
    static class DatabaseSaveLoader extends AsyncTaskLoader<Void>
    {
        private List<Card> mCards;

        public DatabaseSaveLoader(Context context, List<Card> cards)
        {
            super(context);
            this.mCards = cards;
        }

        @Override
        public Void loadInBackground()
        {
            // save to the local database
            for (Card card : mCards)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(CardContract.CardEntry._ID, card.getTitle());
                contentValues.put(CardContract.CardEntry.COLOUMN_DESCRIPTION, card.getDescription());
                contentValues.put(CardContract.CardEntry.COLOUMN_TYPE, card.getType());
                contentValues.put(CardContract.CardEntry.COLOUMN_IMAGE_URL, card.getImageUrl());
                getContext()
                        .getContentResolver()
                        .insert(CardContract.CardEntry.CONTENT_URI, contentValues);
            }


            return null;
        }


    }
}
