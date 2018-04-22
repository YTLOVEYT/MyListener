package com.example.mylistener.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mylistener.utils.CommonUtil;

import java.util.Objects;

/**
 * AIDL 调用返回数据 必须实现parcelable接口
 * Created by YinTao on 2018/4/17.
 */
public class MusicPlaybackTrack implements Parcelable
{
    private long mId;
    private long mSourceId;
    private CommonUtil.IdType mSourceType;
    private int mSourcePosition;

    public MusicPlaybackTrack(long mId, long mSourceId, CommonUtil.IdType mSourceType, int mSourcePosition)
    {
        this.mId = mId;
        this.mSourceId = mSourceId;
        this.mSourceType = mSourceType;
        this.mSourcePosition = mSourcePosition;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(this.mId);
        dest.writeLong(this.mSourceId);
        dest.writeInt(this.mSourceType == null ? -1 : this.mSourceType.ordinal());
        dest.writeInt(this.mSourcePosition);
    }

    protected MusicPlaybackTrack(Parcel in)
    {
        this.mId = in.readLong();
        this.mSourceId = in.readLong();
        int tmpMSourceType = in.readInt();
        this.mSourceType = tmpMSourceType == -1 ? null : CommonUtil.IdType.values()[tmpMSourceType];
        this.mSourcePosition = in.readInt();
    }

    public static final Parcelable.Creator<MusicPlaybackTrack> CREATOR = new Parcelable.Creator<MusicPlaybackTrack>()
    {
        @Override
        public MusicPlaybackTrack createFromParcel(Parcel source)
        {
            return new MusicPlaybackTrack(source);
        }

        @Override
        public MusicPlaybackTrack[] newArray(int size)
        {
            return new MusicPlaybackTrack[size];
        }
    };

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        MusicPlaybackTrack that = (MusicPlaybackTrack) o;
        return mId == that.mId &&
                mSourceId == that.mSourceId &&
                mSourcePosition == that.mSourcePosition &&
                mSourceType == that.mSourceType;
    }
}
