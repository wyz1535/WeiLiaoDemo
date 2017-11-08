package com.leyifu.weiliaodemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hahaha on 2017/11/7 0007.
 */

public class CanvasText extends View {


    private Paint paint;

    private static final String text = "Hello World";
    private static final String text01 = "我爱北京天安门，伟大的祖国万岁";
    private Path path;
    private Paint paint01;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CanvasText(Context context) {
        this(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CanvasText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CanvasText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        //设置下划线
        paint.setUnderlineText(true);
        //设置删除线
        paint.setStrikeThruText(true);
        //设置倾斜角度
        paint.setTextSkewX(0.5f);
        //设置文字横向缩放
        paint.setTextScaleX(1);
        //设置文字间距
        paint.setLetterSpacing(0.2f);
        //设置文字对齐方式
        paint.setTextAlign(Paint.Align.LEFT);

        paint01 = new Paint();
        paint01.setColor(Color.BLACK);
        paint01.setStrokeCap(Paint.Cap.ROUND);
        paint01.setStrokeWidth(20);

        path = new Path();
        path.moveTo(50,100);
        path.quadTo(200,100,100,200);
//        path.quadTo(200,200,500,500);
//        path.quadTo(100,100,120,120);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setTextSize(25);
        canvas.drawPoint(200,100,paint01);
        canvas.drawPath(path,paint);
        canvas.drawTextOnPath(text,path,0,0,paint);

        paint.setTextSize(15);
        paint.setTypeface(Typeface.SERIF);
        canvas.drawText(text,100,200,paint);

        paint.setTextSize(25);
        paint.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText(text,100,250,paint);

        paint.setTextSize(35);
        paint.setTypeface(Typeface.MONOSPACE);
        canvas.drawText(text,100,300,paint);

        paint.setTextSize(45);
        //        设置字体
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(text,100,350,paint);
    }
}
