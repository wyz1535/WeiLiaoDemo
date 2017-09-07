package com.leyifu.weiliaodemo.view;

/**
 * Created by hahaha on 2017/7/24 0024.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;
//import android.widget.TextView;

/**
 * 标签控件
 * */
public class Tab extends TextView {

    private int index = 0;
    private int TextSelectedColor = Color.parseColor("#11B57C");
    private int TextEmptyColor = Color.parseColor("#333333");
    /**
     * 是否选中状态
     * */
    private boolean isSelected = false;
    public Tab(Context context) {
        this(context,null);
    }

    public Tab(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Tab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setTextSize(15);
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        if(isSelected)
            setTextColor(TextSelectedColor);
        else
            setTextColor(TextEmptyColor);
        super.setText(text, type);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        setText(getText());
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public void resetState(){
        isSelected = false;
        setText(getText());
    }

    public void setTextSelectedColor(int textSelectedColor) {
        TextSelectedColor = textSelectedColor;
    }

    public void setTextEmptyColor(int textEmptyColor) {
        TextEmptyColor = textEmptyColor;
    }
}
