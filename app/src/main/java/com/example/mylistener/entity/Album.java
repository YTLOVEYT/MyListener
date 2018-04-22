package com.example.mylistener.entity;

/**
 * 专辑
 * Created by YinTao on 2018/3/22.
 */
public class Album
{
    private long artistId;
    private String artistName;
    private long id;
    private int songCount;
    private String title;
    private int year;

    public Album()
    {
        this.id = -1;
        this.title = "";
        this.artistName = "";
        this.artistId = -1;
        this.songCount = -1;
        this.year = -1;
    }

    public Album(long artistId, String artistName, long id, int songCount, String title, int year)
    {
        this.artistId = artistId;
        this.artistName = artistName;
        this.id = id;
        this.songCount = songCount;
        this.title = title;
        this.year = year;
    }

    public long getArtistId()
    {
        return artistId;
    }

    public void setArtistId(long artistId)
    {
        this.artistId = artistId;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getSongCount()
    {
        return songCount;
    }

    public void setSongCount(int songCount)
    {
        this.songCount = songCount;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    @Override
    public String toString()
    {
        return "Album{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", id=" + id +
                ", songCount=" + songCount +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
    // FIXME: 2018/3/22 是否需要重写equal方法
}

