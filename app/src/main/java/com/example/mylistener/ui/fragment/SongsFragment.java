package com.example.mylistener.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.example.mylistener.Constants;
import com.example.mylistener.ListenerApp;
import com.example.mylistener.R;
import com.example.mylistener.RxBus;
import com.example.mylistener.dao.SortOrder;
import com.example.mylistener.entity.Song;
import com.example.mylistener.event.MediaUpdateEvent;
import com.example.mylistener.event.MetaChangeEvent;
import com.example.mylistener.injector.component.DaggerISongComponent;
import com.example.mylistener.injector.component.IApplicationComponent;
import com.example.mylistener.injector.module.ActivityModule;
import com.example.mylistener.injector.module.SongModule;
import com.example.mylistener.mvp.contract.ISongContract;
import com.example.mylistener.ui.adapter.SongListAdapter;
import com.example.mylistener.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SongsFragment extends Fragment implements ISongContract.iSongView
{
    @Inject
    ISongContract.iSongPresenter songPresenter;
    @BindView(R.id.viewStub_empty)
    ViewStub viewStubEmpty;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private String action;
    private PreferenceUtil preferenceUtil;
    private SongListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupInject();
        songPresenter.attachView(this);
        preferenceUtil = PreferenceUtil.getInstance(getActivity());
        if (getArguments() != null)
        {
            action = getArguments().getString(Constants.PLAYLIST_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        // FIXME: 2018/3/27 改变主题
        songPresenter.loadSong(action);//加载歌曲
        if (Constants.NAVIGATE_PLAYLIST_FAVOURATE.equals(action))
        {
            // FIXME: 2018/4/3 最喜欢
        } else if (Constants.NAVIGATE_PLAYLIST_RECENTPLAY.equals(action))
        {
            // FIXME: 2018/4/3 最近播放
        } else
        {
            subscribeMediaUpdateEvent(); //本页面自带事件
        }
        subscribeMetaChangeEvent();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);//使得fragment 拥有menu菜单
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        songPresenter.unSubscribe();
        RxBus.getRbInstance().unSubscribe(this);
    }

    public static SongsFragment newInstance(String action)
    {
        SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PLAYLIST_TYPE, action);
        fragment.setArguments(args);
        return fragment;
    }

    /** Dagger注入 */
    private void setupInject()
    {
        IApplicationComponent iApplicationComponent = ((ListenerApp) getActivity().getApplication()).getIApplicationComponent();
        DaggerISongComponent.builder()
                .iApplicationComponent(iApplicationComponent)
                .songModule(new SongModule())
                .activityModule(new ActivityModule(getActivity()))
                .build().inject(this);
    }

    private void subscribeMediaUpdateEvent()
    {
        Subscription updateSubscribe = RxBus.getRbInstance().toObservable(MediaUpdateEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(new Action1<MediaUpdateEvent>()
                {
                    @Override
                    public void call(MediaUpdateEvent mediaUpdateEvent)
                    {
                        songPresenter.loadSong(action);
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {

                    }
                });
        RxBus.getRbInstance().addSubscription(this, updateSubscribe);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.song_sort_by, menu);
        if (!action.equals(Constants.NAVIGATE_ALLSONG))
        {
            menu.findItem(R.menu.song_sort_by).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_sort_by_az:
                preferenceUtil.setSongSortOder(SortOrder.SongSortOrder.SONG_AZ);
                songPresenter.loadSong(action);
                return true;
            case R.id.menu_sort_by_za:
                preferenceUtil.setSongSortOder(SortOrder.SongSortOrder.SONG_ZA);
                songPresenter.loadSong(action);
                return true;
            case R.id.menu_sort_by_time:
                preferenceUtil.setSongSortOder(SortOrder.SongSortOrder.SONG_DATE);
                songPresenter.loadSong(action);
                return true;
            case R.id.menu_sort_by_duration:
                preferenceUtil.setSongSortOder(SortOrder.SongSortOrder.SONG_DURATION);
                songPresenter.loadSong(action);
                return true;
            case R.id.menu_sort_by_artist:
                preferenceUtil.setSongSortOder(SortOrder.SongSortOrder.SONG_ARTIST);
                songPresenter.loadSong(action);
                return true;
            case R.id.menu_sort_by_album:
                preferenceUtil.setSongSortOder(SortOrder.SongSortOrder.SONG_ALBUM);
                songPresenter.loadSong(action);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** 注册事件源 */
    private void subscribeMetaChangeEvent()
    {
        Subscription subscribe = RxBus.getRbInstance().toObservable(MetaChangeEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(new Action1<MetaChangeEvent>()
                {
                    @Override
                    public void call(MetaChangeEvent metaChangeEvent)
                    {
                        adapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {
                        //错误处理
                    }
                });
        RxBus.getRbInstance().addSubscription(this, subscribe);
    }

    @Override
    public void showSongs(ArrayList<Song> songs)
    {
        Log.e("ATG", "showSongs: " + songs);
        viewStubEmpty.setVisibility(View.GONE);
        recycleView.setVisibility(View.VISIBLE);
        if (adapter == null)
        {
            recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            //recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            adapter = new SongListAdapter(songs, getActivity(), action, true);
            recycleView.setAdapter(adapter);//设置adapter
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView()
    {
        viewStubEmpty.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.GONE);
    }
}
