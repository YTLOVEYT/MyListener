package com.example.mylistener.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylistener.Constants;
import com.example.mylistener.R;
import com.example.mylistener.RxBus;
import com.example.mylistener.event.MetaChangeEvent;
import com.example.mylistener.ui.fragment.FolderFragment;
import com.example.mylistener.ui.fragment.MainFragment;
import com.example.mylistener.ui.fragment.PlayListFragment;
import com.example.mylistener.ui.fragment.PlayRankingFragment;
import com.example.mylistener.ui.fragment.SearchFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
{
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private HashMap<String, Runnable> navigationMap = new HashMap<>();
    /** drawer的Header的背景 */
    private ImageView albumart;
    /** drawer的Header的title */
    private TextView songtitle;
    /** drawer的Header的歌手 */
    private TextView songartist;

    private Handler handler = new Handler();
    /** 前台Intent */
    private String action;
    /** 是否是夜晚主题 */
    private boolean isDarkTheme = false;
    private Runnable runnable; //当前的任务
    private boolean isSetSlidingLayoutListener = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        action = getIntent().getAction();
        // FIXME: 2018/3/7 判断当前的主题 isDarkTheme

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigationMap.put(Constants.NAVIGATE_LIBRARY, navigateLibrary);//歌曲
        navigationMap.put(Constants.NAVIGATE_ALBUM, navigateAlbum);//专辑
        navigationMap.put(Constants.NAVIGATE_ARTIST, navigateArtist);//歌手

        View headerView = navView.inflateHeaderView(R.layout.nav_header);
        albumart = (ImageView) headerView.findViewById(R.id.album_art);
        songtitle = (TextView) headerView.findViewById(R.id.song_title);
        songartist = (TextView) headerView.findViewById(R.id.song_artist);
        handler.postDelayed(new Runnable() //700毫秒后加载drawer内容
        {
            @Override
            public void run()
            {
                loadDrawerContent(navView);
            }
        }, 700);
        if (Build.VERSION.SDK_INT >= 23)   //检查权限6.0
        {
            checkPermissionThenLoad();
        }
        else
        {
            loadMusic();
        }
        //添加fragment后台监听
        addFragmentBackStackListener();
        // FIXME: 2018/3/7 添加前台Intent事件监听

        subscribeMetaChangeEvent();
    }

    /** 注册事件总线 */
    private void subscribeMetaChangeEvent()
    {
        Subscription subscribe = RxBus.getRbInstance().toObservable(MetaChangeEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()//直到数据改变时 才下发
                .subscribe(new Action1<MetaChangeEvent>()
                {
                    @Override
                    public void call(MetaChangeEvent metaChangeEvent)
                    {
                        setDetailToHeader();
                    }
                }, new Action1<Throwable>()
                {
                    @Override
                    public void call(Throwable throwable)
                    {

                    }
                });
        RxBus.getRbInstance().addSubscription(this, subscribe);
    }

    /** 回显示左菜单的顶部 */
    private void setDetailToHeader()
    {
        // FIXME: 2018/3/19 回显示当前播放的音乐信息


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
                {
                    //如果在播放详情页面，点击右上键，关闭详情页面
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                else if (isMainPage())
                {
                    drawerLayout.openDrawer(Gravity.START);
                }
                else
                {
                    super.onBackPressed();
                }
                return true;
            case R.id.action_search:
                navigateSearch.run();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        // FIXME: 2018/3/12 判断监听器是否添加 添加监听器
        if (!isSetSlidingLayoutListener)
        {

        }

    }

    /** 重写后退 */
    @Override
    public void onBackPressed()
    {
        if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)
        {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        RxBus.getRbInstance().unSubscribe(this);
        // FIXME: 2018/3/12 解除RxBus

    }

    /** 判断当前页面是不是主页面 */
    public boolean isMainPage()
    {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        //// FIXME: 2018/3/12 需要添加判断
        return true;
    }

    /** 初始化drawer内容 */
    private void loadDrawerContent(NavigationView navView)
    {
        modifyNavIcons(navView);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                runnable = null;
                switch (item.getItemId())
                {
                    case R.id.nav_library:
                        runnable = navigateLibrary;
                        break;
                    case R.id.nav_playlists:
                        runnable = navigatePlaylist;
                        break;
                    case R.id.nav_folders:
                        runnable = navigateFolders;
                        break;
                    case R.id.nav_favourate:
                        runnable = navigateFavourate;
                        break;
                    case R.id.nav_recent_play:
                        runnable = navigateRecentPlay;
                        break;
                    case R.id.nav_recent_add:
                        runnable = navigateRecentAdd;
                        break;
                    case R.id.nav_play_ranking:
                        runnable = navigatePlayRanking;
                        break;
                    case R.id.nav_settings:
                        runnable = navigateSetting;
                        break;
                    case R.id.nav_exit:
                        MainActivity.this.finish();
                        break;
                }
                if (runnable != null)
                {
                    drawerLayout.closeDrawers();//关闭
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            runnable.run();
                        }
                    }, 350);
                }
                return true;
            }
        });
    }

    /** 修改NAV的图标 */
    private void modifyNavIcons(NavigationView navView)
    {
        if (!isDarkTheme)
        {
            navView.getMenu().findItem(R.id.nav_library).setIcon(R.drawable.ic_music_note_black_48dp);
            navView.getMenu().findItem(R.id.nav_playlists).setIcon(R.drawable.ic_queue_music_black_48dp);
            navView.getMenu().findItem(R.id.nav_folders).setIcon(R.drawable.ic_folder_black_48dp);
            navView.getMenu().findItem(R.id.nav_favourate).setIcon(R.drawable.ic_favorite_black_48dp);
            navView.getMenu().findItem(R.id.nav_recent_play).setIcon(R.drawable.ic_watch_later_black_48dp);
            navView.getMenu().findItem(R.id.nav_recent_add).setIcon(R.drawable.ic_add_box_black_48dp);
            navView.getMenu().findItem(R.id.nav_play_ranking).setIcon(R.drawable.ic_sort_black_48dp);
            navView.getMenu().findItem(R.id.nav_settings).setIcon(R.drawable.ic_settings_black_48dp);
            navView.getMenu().findItem(R.id.nav_exit).setIcon(R.drawable.ic_exit_to_app_black_48dp);
        }
        else
        {
            navView.getMenu().findItem(R.id.nav_library).setIcon(R.drawable.ic_music_note_white_48dp);
            navView.getMenu().findItem(R.id.nav_playlists).setIcon(R.drawable.ic_queue_music_white_48dp);
            navView.getMenu().findItem(R.id.nav_folders).setIcon(R.drawable.ic_folder_white_48dp);
            navView.getMenu().findItem(R.id.nav_favourate).setIcon(R.drawable.ic_favorite_white_48dp);
            navView.getMenu().findItem(R.id.nav_recent_play).setIcon(R.drawable.ic_watch_later_white_48dp);
            navView.getMenu().findItem(R.id.nav_recent_add).setIcon(R.drawable.ic_add_box_white_48dp);
            navView.getMenu().findItem(R.id.nav_play_ranking).setIcon(R.drawable.ic_sort_white_48dp);
            navView.getMenu().findItem(R.id.nav_settings).setIcon(R.drawable.ic_settings_white_48dp);
            navView.getMenu().findItem(R.id.nav_exit).setIcon(R.drawable.ic_exit_to_app_white_48dp);
        }
    }

    /** 添加fragment 的退栈监听 */
    private void addFragmentBackStackListener()
    {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
        {
            @Override
            public void onBackStackChanged()
            {
                getSupportFragmentManager().findFragmentById(R.id.fragment_container).onResume();
            }
        });

    }

    /** 加载内容 */
    private void loadMusic()
    {
        Runnable runnable = navigationMap.get(action);
        if (runnable != null)
        {
            runnable.run();
        }
        else
        {
            navigateLibrary.run();
        }
        new initQuickControl().execute("");
    }

    /** 检查权限是否授权，若授权则加载数据 */
    private void checkPermissionThenLoad()
    {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, "") == PackageManager.PERMISSION_GRANTED)
        {
            //直接加载
            loadMusic();
        }
        else
        {
            //用户首次拒绝后访问权限为true
            boolean b = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (b)
            {
                Toast.makeText(this, "跳向设置界面打开权限", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    loadMusic();
                }
                else
                {
                    // FIXME: 2018/3/20 权限请求失败
                    Toast.makeText(MainActivity.this, "有问题", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // runnable任务
    ///////////////////////////////////////////////////////////////////////////
    /** 搜索0 */
    private Runnable navigateSearch = new Runnable()
    {
        @Override
        public void run()
        {
            SearchFragment fragment = new SearchFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
            transaction.add(R.id.fragment_container, fragment);
            transaction.addToBackStack(null).commit();
        }
    };
    /** 专辑 1 */
    private Runnable navigateAlbum = new Runnable()
    {
        @Override
        public void run()
        {
            // FIXME: 2018/3/20 navigateAlbum这个是干嘛的
            //            long albumId = getIntent().getExtras().getLong(Constants.ALBUM_ID);
            //            String albumName = getIntent().getExtras().getString(Constants.ALBUM_NAME);
            //            AlbumDetailFragment detailFragment = AlbumDetailFragment.newInstance(albumId, albumName, false, null);
            //            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
        }
    };
    /** 歌手 2 */
    private Runnable navigateArtist = new Runnable()
    {
        @Override
        public void run()
        {
            // FIXME: 2018/3/20 navigateAlbum这个是干嘛的
            //            long artistId = getIntent().getExtras().getLong(Constants.ARTIST_ID);
            //            String artistName = getIntent().getExtras().getString(Constants.ARTIST_ID);
            //            ArtistDetailFragment detailFragment = ArtistDetailFragment.newInstance(artistId, artistName, false, null);
            //            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
        }
    };
    /** 我的歌曲 3 */
    private Runnable navigateLibrary = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_library).setChecked(true);
            //显示mainFragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, MainFragment.newInstance(Constants.NAVIGATE_ALLSONG)).commitAllowingStateLoss();
        }
    };

    /** 我的歌单 4 */
    private Runnable navigatePlaylist = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_playlists).setChecked(true);
            PlayListFragment playListFragment = new PlayListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
            transaction.replace(R.id.fragment_container, playListFragment).commit();
        }
    };
    /** 文件夹 5 */
    private Runnable navigateFolders = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_folders).setChecked(true);
            FolderFragment folderFragment = new FolderFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
            transaction.replace(R.id.fragment_container, folderFragment).commit();
        }
    };
    /** 我喜欢 6 */
    private Runnable navigateFavourate = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_folders).setChecked(true);
            FolderFragment folderFragment = new FolderFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
            transaction.replace(R.id.fragment_container, folderFragment).commit();
        }
    };
    /** 最近播放7 */
    private Runnable navigateRecentPlay = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_recent_play).setChecked(true);
            MainFragment mainFragment = MainFragment.newInstance(Constants.NAVIGATE_PLAYLIST_RECENTPLAY);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mainFragment).commit();
        }
    };
    /** 最近添加 8 */
    private Runnable navigateRecentAdd = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_recent_add).setChecked(true);
            MainFragment mainFragment = MainFragment.newInstance(Constants.NAVIGATE_PLAYLIST_RECENTADD);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mainFragment).commit();
        }
    };
    /** 播放排行 9 */
    private Runnable navigatePlayRanking = new Runnable()
    {
        @Override
        public void run()
        {
            navView.getMenu().findItem(R.id.nav_play_ranking).setChecked(true);
            PlayRankingFragment rankingFragment = new PlayRankingFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
            transaction.replace(R.id.fragment_container, rankingFragment).commit();
        }
    };
    /** 设置 10 */
    private Runnable navigateSetting = new Runnable()
    {
        @Override
        public void run()
        {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            MainActivity.this.startActivity(intent);
        }
    };

}
