package com.example.mylistener.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylistener.R;
import com.example.mylistener.entity.Song;
import com.example.mylistener.utils.ATEUtil;
import com.example.mylistener.utils.SongUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌曲显示适配器
 * Created by YinTao on 2018/3/20.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder>
{
    private static final String TAG = "SongListAdapter";
    private ArrayList<Song> songs;
    private Context context;
    private int curPlayPosition;//最近播放的位置
    private boolean withHeader;//是否带头部
    private String action;


    public SongListAdapter(ArrayList<Song> songs, Context context, String action, boolean withHeader)
    {
        if (songs == null)
        {
            this.songs = new ArrayList<>();
        } else
        {
            this.songs = songs;
        }
        this.context = context;
        this.action = action;
        this.withHeader = withHeader;
    }

    /** 布局构造 */
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        SongViewHolder holder = null;
        switch (viewType)
        {
            case Type.TYPE_PLAY_SHUFFLE: //头部
                View shuffleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play_shuffle, parent, false);
                ImageView sView = (ImageView) shuffleView.findViewById(R.id.play_shuffle);
                // FIXME: 2018/4/3 设置图标的ATE
                holder = new SongViewHolder(shuffleView);
                break;
            case Type.TYPE_SONG: //歌曲
                View songView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song, parent, false);
                holder = new SongViewHolder(songView);
                break;
        }
        return holder;
    }

    /** 页面显示类型 */
    public static class Type
    {
        private static final int TYPE_SONG = 1;
        private static final int TYPE_PLAY_SHUFFLE = 0;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        switch (viewType)
        {
            case Type.TYPE_PLAY_SHUFFLE:
                break;
            case Type.TYPE_SONG:
                Song song;
                if (withHeader)
                {
                    song = songs.get(position - 1);
                    Log.e(TAG, "onBindViewHolder: " + song);
                } else
                {
                    song = songs.get(position);
                }
                holder.title.setText(song.getTitle());
                holder.artist.setText(song.getArtistName());
                holder.album.setText(song.getAlbumName());
                Glide.with(holder.itemView.getContext()).load(SongUtil.getAlbumArtUri(song.getAlbumId()).toString())
                        .error(ATEUtil.getDefaultAlbumDrawable(context))
                        .placeholder(ATEUtil.getDefaultAlbumDrawable(context))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .centerCrop()
                        .into(holder.albumArt);

                // FIXME: 2018/4/8 判断当前的播放歌曲 设置列表点击样式

                break;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0 && withHeader)
        {
            return Type.TYPE_PLAY_SHUFFLE;
        } else
        {
            return Type.TYPE_SONG;
        }
    }

    @Override
    public int getItemCount()
    {
        return songs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;//名字
        private TextView artist;//歌曲家
        private TextView album;//专辑
        private ImageView albumArt;//专辑图片
        private ImageView popupMenu;//
        private View playscore;

        public SongViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_item_title);
            artist = (TextView) itemView.findViewById(R.id.text_item_subtitle);
            album = (TextView) itemView.findViewById(R.id.text_item_subtitle_2);
            albumArt = (ImageView) itemView.findViewById(R.id.image);
            popupMenu = (ImageView) itemView.findViewById(R.id.popup_menu);
            playscore = itemView.findViewById(R.id.playScore);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "点击" + (getAdapterPosition() - 1), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void setSongList(ArrayList<Song> songs)
    {
        this.songs = songs;
        Log.e(TAG, "setSongList: " + songs);
        // FIXME: 2018/4/10 获取歌曲的id干哈
        if (songs.size() > 0)
        {

        }
        notifyDataSetChanged();
    }


}
