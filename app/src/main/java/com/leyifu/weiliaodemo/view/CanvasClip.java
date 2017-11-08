package com.leyifu.weiliaodemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.leyifu.weiliaodemo.R;

/**
 * Created by hahaha on 2017/11/8 0008.
 */

public class CanvasClip extends View {

    private Path path;
    private Bitmap bitmap;
    private Paint paint;

    public CanvasClip(Context context) {
        this(context,null);
    }

    public CanvasClip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasClip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        path = new Path();
        path.addCircle(250,475,150,Path.Direction.CW);


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar_01);

        paint = new Paint();
        paint.setStrokeWidth(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.clipRect(125,100,325,300);
        canvas.drawBitmap(bitmap,0,0, paint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.restore();
    }
}
