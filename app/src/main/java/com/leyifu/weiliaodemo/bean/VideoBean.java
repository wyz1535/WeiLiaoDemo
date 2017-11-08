package com.leyifu.weiliaodemo.bean;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by hahaha on 2017/11/2 0002.
 */

public class VideoBean {

    private int id;
    private String name;
    private String path;
    private long size;
    private long duration;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    public void reuse(Cursor cursor) {
        this.path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
        this.name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME));
        this.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE));
        this.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
        this.id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                '}';
    }
}
