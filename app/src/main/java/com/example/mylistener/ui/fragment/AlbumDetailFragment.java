package com.example.mylistener.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylistener.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailFragment extends Fragment
{


    public static AlbumDetailFragment newInstance(long id, String name, boolean useTransition, String transitionName)
    {
        AlbumDetailFragment fragment = new AlbumDetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_album_detail, container, false);
    }

}
