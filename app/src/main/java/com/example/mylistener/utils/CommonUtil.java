package com.example.mylistener.utils;

/**
 * 公共工具类
 * Created by YinTao on 2018/4/17.
 */
public class CommonUtil
{
    /** 歌曲ID信息 */
    public enum IdType
    {
        NA(0),
        ARTIST(1),
        ALBUM(2),
        PLAYLIST(3),
        FOLDER(4);

        private final int id;

        IdType(final int id)
        {
            this.id = id;
        }

        public static IdType getTypeById(int id)
        {
            for (IdType type : values())
            {
                if (type.id == id)
                {
                    return type;
                }
            }
            throw  new IllegalArgumentException("unRecognized Id");
        }


    }

}
