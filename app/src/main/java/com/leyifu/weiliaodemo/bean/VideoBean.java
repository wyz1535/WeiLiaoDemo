package com.leyifu.weiliaodemo.bean;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

/**
 * Created by hahaha on 2017/11/2 0002.
 */

public class VideoBean implements Parcelable{

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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(path);
        out.writeLong(size);
        out.writeLong(duration);
    }

    public static final Parcelable.Creator<VideoBean> CREATOR
            = new Parcelable.Creator<VideoBean>() {
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    public VideoBean() {
    }

    private VideoBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        path = in.readString();
        size = in.readLong();
        duration = in.readLong();
    }
}
