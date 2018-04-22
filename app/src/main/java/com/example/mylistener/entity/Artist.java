package com.example.mylistener.entity;

/**
 * 歌手
 * Created by YinTao on 2018/3/22.
 */
public class Artist
{
    private int albumCount;
    private long id;
    private String name;
    private int songCount;

    public Artist()
    {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
        this.albumCount = -1;
    }

    public Artist(long _id, String _name, int _albumCount, int _songCount)
    {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
        this.albumCount = _albumCount;
    }

    public int getAlbumCount()
    {
        return albumCount;
    }

    public void setAlbumCount(int albumCount)
    {
        this.albumCount = albumCount;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSongCount()
    {
        return songCount;
    }

    public void setSongCount(int songCount)
    {
        this.songCount = songCount;
    }

    @Override
    public String toString()
    {
        return "Artist{" +
                "albumCount=" + albumCount +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", songCount=" + songCount +
                '}';
    }
}
