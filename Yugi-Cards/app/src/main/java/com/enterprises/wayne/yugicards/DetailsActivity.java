package com.enterprises.wayne.yugicards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity
{

    /* UI */
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageViewCard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // reference view
        textViewTitle = (TextView) findViewById(R.id.text_view_title);
        textViewDescription = (TextView) findViewById(R.id.text_view_title);
        imageViewCard = (ImageView) findViewById(R.id.image_view_card);
        
    }
}
