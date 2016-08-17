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

    /* UI */
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageViewCard;

    /* fields */
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setTitle(getString(R.string.card_details));

        // reference view
        textViewTitle = (TextView) findViewById(R.id.text_view_title);
        textViewDescription = (TextView) findViewById(R.id.text_view_description);
        imageViewCard = (ImageView) findViewById(R.id.image_view_card);

        // get data from the intent
        if (getIntent() != null && getIntent().hasExtra(EXTRAS_CARD))
        {
            card = (Card) getIntent().getSerializableExtra(EXTRAS_CARD);
            textViewTitle.setText(card.getTitle());
            imageViewCard.setImageResource(R.mipmap.ic_launcher);
            textViewDescription.setText(card.getDescription());
            Picasso.with(this)
                    .load(card.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageViewCard);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_item_share)
            if (card != null)
            {
                // share the card's description
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, card.getTitle() + "\n" + card.getDescription());
                sendIntent.setType("text/plain");
                Intent chooserIntent = Intent.createChooser(sendIntent, getString(R.string.share_card));

                // Verify that the intent will resolve to an activity
                if (chooserIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(chooserIntent);
            }

        return super.onOptionsItemSelected(item);
    }
}
