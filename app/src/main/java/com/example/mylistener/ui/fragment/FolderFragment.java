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
public class FolderFragment extends Fragment
{


    public FolderFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

}
