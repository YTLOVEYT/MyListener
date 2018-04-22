package com.example.mylistener.mvp.usecase;

import com.example.mylistener.Constants;
import com.example.mylistener.entity.Song;
import com.example.mylistener.respository.interfaces.IRepository;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 获取歌曲
 * Created by YinTao on 2018/3/21.
 */

public class GetSongsCase extends ACase<GetSongsCase.RequestValue, GetSongsCase.ResponseValue>
{
    private final IRepository repository;

    public GetSongsCase(IRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public ResponseValue execute(RequestValue requestValue)
    {
        String action = requestValue.getAction();
        switch (action)
        {
            case Constants.NAVIGATE_PLAYLIST_RECENTADD://最近添加歌曲
                return new ResponseValue(repository.getAllSongs());
            case Constants.NAVIGATE_PLAYLIST_RECENTPLAY://最近播放歌曲
                return new ResponseValue(repository.getAllSongs());
            case Constants.NAVIGATE_ALLSONG://所有歌曲
                return new ResponseValue(repository.getAllSongs());
            case Constants.NAVIGATE_PLAYLIST_TOPPLAYED://播放量最高的
                return new ResponseValue(repository.getAllSongs());
            case Constants.NAVIGATE_PLAYLIST_FAVOURATE://最喜欢歌曲
                return new ResponseValue(repository.getAllSongs());
            default:
                throw new RuntimeException("wrong action type");
        }
    }

    /** 请求行动 */
    public static final class RequestValue implements ACase.RequestValue
    {
        private final String action; //只能通过构造函数传入

        public RequestValue(String action)
        {
            this.action = action;
        }

        public String getAction()
        {
            return action;
        }

    }

    /** 返回结果 */
    public static final class ResponseValue implements ACase.ResponseValue
    {
        private final Observable<ArrayList<Song>> songList;

        public ResponseValue(Observable<ArrayList<Song>> songList)
        {
            this.songList = songList;
        }

        public Observable<ArrayList<Song>> getSongList()
        {
            return songList;
        }
    }
}
