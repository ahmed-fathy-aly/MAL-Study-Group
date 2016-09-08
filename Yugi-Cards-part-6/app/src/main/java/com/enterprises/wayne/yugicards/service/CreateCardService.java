package com.enterprises.wayne.yugicards.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;

import com.enterprises.wayne.yugicards.event.DatabaseUpdatedEvent;
import com.enterprises.wayne.yugicards.database.CardContract;

import org.greenrobot.eventbus.EventBus;

/**
 * a service that saves a card to the local database
 */
public class CreateCardService extends IntentService
{

    /* constants */
    private static final String EXTRA_TITLE = "com.enterprises.wayne.yugicards.extra.title";
    private static final String EXTRA_DESCRIPTION = "com.enterprises.wayne.yugicards.extra.description";
    private static final String EXTRA_IMAGE_URL = "com.enterprises.wayne.yugicards.extra.image";
    private static final String EXTRA_TYPE = "com.enterprises.wayne.yugicards.extra.type";

    public CreateCardService()
    {
        super("CreateCardService");
    }

    /**
     * Starts this service the given parameters.
     * If the service is already performing a task this action will be queued.
     */
    public static void startSavingCard(Context context, String title,
                                       String description, String imageURL, String type)
    {
        Intent intent = new Intent(context, CreateCardService.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_IMAGE_URL, imageURL);
        intent.putExtra(EXTRA_TYPE, type);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        // save to local database
        ContentValues contentValues = new ContentValues();
        contentValues.put(CardContract.CardEntry._ID, intent.getStringExtra(EXTRA_TITLE));
        contentValues.put(CardContract.CardEntry.COLOUMN_DESCRIPTION, intent.getStringExtra(EXTRA_DESCRIPTION));
        contentValues.put(CardContract.CardEntry.COLOUMN_TYPE, intent.getStringExtra(EXTRA_TYPE));
        contentValues.put(CardContract.CardEntry.COLOUMN_IMAGE_URL, intent.getStringExtra(EXTRA_IMAGE_URL));
        getContentResolver()
                .insert(CardContract.CardEntry.CONTENT_URI, contentValues);


        // notify that the database is updated
        EventBus.getDefault().post(new DatabaseUpdatedEvent());
    }

}
