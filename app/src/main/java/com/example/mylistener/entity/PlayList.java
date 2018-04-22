package com.example.mylistener.entity;

/**
 * Created by YinTao on 2018/3/22.
 */

public class PlayList
{
    private long id;
    private String name;
    private int songCount;

    public PlayList()
    {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
    }

    public PlayList(long _id, String _name, int _songCount)
    {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
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
        return "PlayList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", songCount=" + songCount +
                '}';
    }
}
