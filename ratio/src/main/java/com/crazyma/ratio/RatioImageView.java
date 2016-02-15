package com.crazyma.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by david on 2016/2/15.
 */
public class RatioImageView extends ImageView implements RatioView{

    private int ratioWidth,ratioHeight;
    private int dependOn = ON_WIDTH;

    private OnMeasureFinishedListener onMeasureFinishedListener;
    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public OnMeasureFinishedListener getOnMeasureFinishedListener() {
        return onMeasureFinishedListener;
    }

    public void setOnMeasureFinishedListener(OnMeasureFinishedListener onMeasureFinishedListener) {
        this.onMeasureFinishedListener = onMeasureFinishedListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int newWidth,newHeight;

        if(dependOn == 0){  //  depend on width
            newWidth = widthSize;
            newHeight = (int)(widthSize * ratioHeight / (float)ratioWidth);
        }else{  //  depend on height
            newWidth = (int)(heightSize * ratioWidth / (float) ratioHeight);
            newHeight = heightSize;
        }

        final int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
        final int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);

        if(onMeasureFinishedListener != null)
            onMeasureFinishedListener.onMeasureFinished(newWidth,newHeight);
    }

}
