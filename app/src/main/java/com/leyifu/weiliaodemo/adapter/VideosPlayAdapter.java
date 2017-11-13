package com.leyifu.weiliaodemo.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.bean.VideoBean;
import com.leyifu.weiliaodemo.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hahaha on 2017/11/2 0002.
 */

public class VideosPlayAdapter extends RecyclerView.Adapter<VideosPlayAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<VideoBean> list;
    private Cursor cursor;
    private ViewHolder viewHolder;

    private Map<Integer, String> thumbnailsMap = new HashMap<>();

    public VideosPlayAdapter(Context context) {
        this.mContext = context;
        ContentResolver contentResolver = mContext.getContentResolver();

        new VideoTask().execute(contentResolver);
    }

    private class VideoTask extends AsyncTask<ContentResolver, Void, Cursor> {


        @Override
        protected Cursor doInBackground(ContentResolver... params) {
            ContentResolver resolver = (ContentResolver) params[0];
            Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//            list = new ArrayList<>();
//            while (cursor.moveToNext()) {
//                VideoBean videoBean = new VideoBean();
//                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
//                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME));
//                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE));
//                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
//                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
//                videoBean.setPath(path);
//                videoBean.setName(name);
//                videoBean.setSize(size);
//                videoBean.setDuration(duration);
//
//                list.add(videoBean);
////
//                Log.e("doInBackground", "path: " + path + "&name:" + name + "&size:" + size + "&duration:" + duration);
//            }
//            cursor.close();

            Cursor thumbnailCursor = resolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, null, null, null, null);
            while (thumbnailCursor.moveToNext()) {
                int thumbnailId = thumbnailCursor.getInt(thumbnailCursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
                String path = thumbnailCursor.getString(thumbnailCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                thumbnailsMap.put(thumbnailId, path);
            }
            thumbnailCursor.close();
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            VideosPlayAdapter.this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public void recycle() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_video_list_item, parent, false);
        view.setOnClickListener(this);
        VideoBean videoBean = new VideoBean();
        viewHolder = new ViewHolder(view, videoBean);
        view.setTag(viewHolder);
        return viewHolder;
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickLisntener != null) {
            ViewHolder viewHolder = (ViewHolder) v.getTag();
            mOnItemClickLisntener.onItemClick(v,viewHolder.videoBean);
        }
    }


    private OnItemClickLListener mOnItemClickLisntener;

    public void setmOnItemClickLisntener(OnItemClickLListener onItemClickLisntener) {
        this.mOnItemClickLisntener = onItemClickLisntener;
    }

    public interface OnItemClickLListener {
        void onItemClick(View view,VideoBean videoBean);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        cursor.moveToPosition(position);

        VideoBean videoBean = holder.videoBean;

        videoBean.reuse(cursor);

        holder.bind();
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_icon;
        private final TextView tv_name;
        private final TextView tv_size;
        private final TextView item_duration;
        private VideoBean videoBean;
        private Object thumbnailToPath;

        public ViewHolder(View itemView, VideoBean videoBean) {
            super(itemView);
            this.videoBean = videoBean;
            iv_icon = ((ImageView) itemView.findViewById(R.id.iv_icon));
            tv_name = ((TextView) itemView.findViewById(R.id.tv_name));
            tv_size = ((TextView) itemView.findViewById(R.id.tv_size));
            item_duration = ((TextView) itemView.findViewById(R.id.item_duration));
        }

        public void bind() {
            tv_name.setText(videoBean.getName());
            tv_size.setText(Formatter.formatFileSize(mContext, videoBean.getSize()));
            item_duration.setText(Utils.formatterTime(videoBean.getDuration()));

            String thumbnailPath = thumbnailsMap.get(videoBean.getId());
            Log.e("thumbnailPath", "bind: " + thumbnailPath);
            if (thumbnailPath == null) {


                getThumbnailToPath(videoBean.getPath());
            } else {
                iv_icon.setImageBitmap(BitmapFactory.decodeFile(thumbnailPath));
            }
        }

        public void getThumbnailToPath(String path) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            Bitmap bitmap = retriever.getFrameAtTime();
            iv_icon.setImageBitmap(bitmap);
        }
    }
}
