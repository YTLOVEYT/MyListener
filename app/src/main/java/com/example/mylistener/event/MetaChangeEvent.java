package com.example.mylistener.event;

/**
 * 播放歌曲改变时的消息类型封装
 * Created by YinTao on 2018/3/19.
 */

public class MetaChangeEvent
{
    private long songId;
    private String songName;
    private String artistName;

    public MetaChangeEvent(long songId, String songName, String artistName)
    {
        this.songId = songId;
        this.songName = songName;
        this.artistName = artistName;
    }

    public long getSongId()
    {
        return songId;
    }

    public void setSongId(long songId)
    {
        this.songId = songId;
    }

    public String getSongName()
    {
        return songName;
    }

    public void setSongName(String songName)
    {
        this.songName = songName;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    @Override
    public String toString()
    {
        return "MetaChangeEvent{" +
                "songId=" + songId +
                ", songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof MetaChangeEvent && ((MetaChangeEvent) obj).getSongId() == songId;
    }
}
