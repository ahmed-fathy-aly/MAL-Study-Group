package com.enterprises.wayne.yugicards.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enterprises.wayne.yugicards.R;
import com.enterprises.wayne.yugicards.entity.Card;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment
{
    /* constants */
    public static String ARG_CARD;

    /* UI */
    private TextView textViewTitle;
    private TextView textViewDescription;
    private ImageView imageViewCard;

    public DetailsFragment()
    {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public static DetailsFragment newInstance(Card card)
    {
        DetailsFragment fragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CARD, card);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // reference view
        textViewTitle = (TextView) view.findViewById(R.id.text_view_title);
        textViewDescription= (TextView) view.findViewById(R.id.text_view_description);
        imageViewCard = (ImageView) view.findViewById(R.id.image_view_card);

        // get data from the arguments
        Card card = (Card) getArguments().getSerializable(ARG_CARD);
        textViewTitle.setText(card.getTitle());
        imageViewCard.setImageResource(R.mipmap.ic_launcher);
        textViewDescription.setText(card.getDescription());
        Picasso.with(getContext())
                .load(card.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageViewCard);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Card card = (Card) getArguments().getSerializable(ARG_CARD);
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
                if (chooserIntent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(chooserIntent);
            }

        return super.onOptionsItemSelected(item);
    }

}
