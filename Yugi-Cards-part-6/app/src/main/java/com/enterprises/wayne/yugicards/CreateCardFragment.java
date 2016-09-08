package com.enterprises.wayne.yugicards;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;


/**
 */
public class CreateCardFragment extends Fragment
{

    /* UI */
    ImageView imageViewCard;
    EditText editTextTitle, editTextDescription, editTextImageURL;
    Button buttonCreate;
    Spinner spinnerCardType;

    /**
     * only use this to instantiate the fragment
     */
    public static CreateCardFragment newInstance()
    {
        return new CreateCardFragment();
    }

    public CreateCardFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_card, container, false);

        // reference views
        imageViewCard = (ImageView) view.findViewById(R.id.image_view_card);
        editTextTitle = (EditText) view.findViewById(R.id.edit_text_title);
        editTextDescription= (EditText) view.findViewById(R.id.edit_text_description);
        editTextImageURL = (EditText) view.findViewById(R.id.edit_text_image_url);
        buttonCreate = (Button) view.findViewById(R.id.button_create);
        spinnerCardType = (Spinner) view.findViewById(R.id.spinner_type);

        // add listeners
        buttonCreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createCard();
            }
        });
        editTextImageURL.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                // lazy load the image
                String text = editable.toString();
                Picasso
                        .with(getContext())
                        .load(text)
                        .into(imageViewCard);
            }
        });
        return view;
    }

    /**
     * gathers the entered data and launches a services that adds the card to the database
     */
    private void createCard()
    {
        // gather data
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String imageUrl = editTextImageURL.getText().toString();
        String type = (String) spinnerCardType.getSelectedItem();
        type = Character.toUpperCase(type.charAt(0)) + type.substring(1);

        // launch the service
        CreateCardService.startSavingCard(getContext(), title, description, imageUrl, type);
    }

}
