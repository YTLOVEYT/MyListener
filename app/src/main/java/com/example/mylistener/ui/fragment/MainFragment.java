package com.example.mylistener.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylistener.Constants;
import com.example.mylistener.R;
import com.example.mylistener.utils.PreferenceUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MainFragment
 */
public class MainFragment extends Fragment
{

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private String action;
    private PreferenceUtil preference;

    public static MainFragment newInstance(String action)
    {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PLAYLIST_TYPE, action);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        preference = PreferenceUtil.getInstance(getActivity());
        if (getArguments() != null)
        {
            action = getArguments().getString(Constants.PLAYLIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // FIXME: 2018/3/14 小于5.0的要去除状态栏

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        switch (action)
        {
            case Constants.NAVIGATE_ALLSONG://所有的歌曲
                actionBar.setTitle(R.string.library);
                break;
            case Constants.NAVIGATE_PLAYLIST_RECENTADD://最近添加
                actionBar.setTitle(R.string.recent_add);
                break;
            case Constants.NAVIGATE_PLAYLIST_RECENTPLAY://最近播放
                actionBar.setTitle(R.string.library);
                break;
            case Constants.NAVIGATE_PLAYLIST_FAVOURATE://喜欢
                actionBar.setTitle(R.string.favourate);
                break;
        }
        if (viewpager != null)
        {
            setupViewPager(viewpager);
            viewpager.setCurrentItem(preference.getStartPageIndex());
            viewpager.setOffscreenPageLimit(2);
        }
        tabs.setupWithViewPager(viewpager);
    }

    /** 设置Viewpager */
    private void setupViewPager(ViewPager viewpager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(SongsFragment.newInstance(action), this.getString(R.string.songs));
        adapter.addFragment(ArtistFragment.newInstance(action), this.getString(R.string.artists));
        adapter.addFragment(AlbumFragment.newInstance(action), this.getString(R.string.albums));
        viewpager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> fragments = new ArrayList<>();
        private ArrayList<String> ts = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            ts.add(title);
        }


        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return ts.get(position);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
}
