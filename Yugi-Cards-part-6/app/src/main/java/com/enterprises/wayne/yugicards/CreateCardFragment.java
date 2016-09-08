package com.enterprises.wayne.yugicards;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 */
public class CreateCardFragment extends Fragment
{


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
        return inflater.inflate(R.layout.fragment_create_card, container, false);
    }

}
