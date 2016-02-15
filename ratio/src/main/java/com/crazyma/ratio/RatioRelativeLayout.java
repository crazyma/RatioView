package com.crazyma.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by david on 2016/2/15.
 */
public class RatioRelativeLayout extends RelativeLayout implements RatioView{

    private int ratioWidth = 16;
    private int ratioHeight = 9;
    private int dependOn = ON_WIDTH;

    private OnMeasureFinishedListener onMeasureFinishedListener;
    public RatioRelativeLayout(Context context) {
        super(context);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RatioView,
                0, 0);

        try {
            ratioHeight = a.getInt(R.styleable.RatioView_ratio_height, 9);
            ratioWidth = a.getInt(R.styleable.RatioView_ratio_width, 16);
            dependOn = a.getInteger(R.styleable.RatioView_dependOn, ON_WIDTH);
        } finally {
            a.recycle();
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int newWidth,newHeight;

        if(dependOn == 0){  //  depend on width
            newWidth = widthSize;
            newHeight = (int)(widthSize * ratioHeight / (float)ratioWidth);
        }else{  //  depend on height
            newWidth = (int)(heightSize * ratioWidth / (float) ratioHeight);
            newHeight = heightSize;
        }

        final int newWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(newWidth, View.MeasureSpec.EXACTLY);
        final int newHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(newHeight, View.MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        if(onMeasureFinishedListener != null)
            onMeasureFinishedListener.onMeasureFinished(newWidth,newHeight);
    }

    public int getDependOn() {
        return dependOn;
    }

    public void setDependOn(int dependOn) {
        this.dependOn = dependOn;
        requestLayout();
        invalidate();
    }

    public int getRatioWidth() {
        return ratioWidth;
    }

    public void setRatio(int ratioWidth,int ratioHeight){
        this.ratioWidth = ratioWidth;
        this.ratioHeight = ratioHeight;
        requestLayout();
        invalidate();
    }

    public int getRatioHeight() {
        return ratioHeight;
    }

    public OnMeasureFinishedListener getOnMeasureFinishedListener() {
        return onMeasureFinishedListener;
    }

    public void setOnMeasureFinishedListener(OnMeasureFinishedListener onMeasureFinishedListener) {
        this.onMeasureFinishedListener = onMeasureFinishedListener;
    }
}
