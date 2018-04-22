package com.example.mylistener.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylistener.Constants;
import com.example.mylistener.R;


public class ArtistFragment extends Fragment
{
    private String action;

    public ArtistFragment()
    {

    }

    public static ArtistFragment newInstance(String action)
    {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PLAYLIST_TYPE, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            action = getArguments().getString(Constants.PLAYLIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

}
