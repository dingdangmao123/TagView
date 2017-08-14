package com.dingdangmao.tagview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by suxiaohui on 2017/8/13.
 */

public class TagView extends ViewGroup {

    float bg_radius;
    float bg_border_width;
    int bg_color;
    int bg_alpha;
    Paint p=new Paint();

    public TagView(Context context) {
        this(context,null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, 0);

        try {

            bg_radius = attr.getDimension(R.styleable.TagView_bg_radius,5);
            bg_alpha = attr.getInteger(R.styleable.TagView_bg_alpha,100);
            bg_color = attr.getColor(R.styleable.TagView_bg_color,Color.parseColor("#FFCCCC"));
            bg_border_width=attr.getFloat(R.styleable.TagView_bg_radius,1);

        } catch (Exception e) {

            Log.i("Unit", e.toString());

        } finally {

            attr.recycle();
        }
        setWillNotDraw(false);
        p = new Paint();
        p.setColor(bg_color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
       // Log.i("Unit","widthsize"+String.valueOf(widthSize));
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int num = getChildCount();
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPadding = getPaddingTop();
        int bPadding = getPaddingBottom();

        int height = tPadding + bPadding;
        int width = lPadding + rPadding;

        for (int i = 0; i < num; i++) {
            View c = getChildAt(i);

            MarginLayoutParams mp = (MarginLayoutParams) c.getLayoutParams();
            if (width + c.getMeasuredWidth() + mp.leftMargin + mp.rightMargin > widthSize) {
                width = lPadding + rPadding + c.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;
                if (height + c.getHeight() + mp.topMargin + mp.bottomMargin > heightSize)
                    break;
                height += c.getMeasuredHeight() + mp.topMargin + mp.bottomMargin;
            } else {
                width += c.getMeasuredWidth() + mp.leftMargin + mp.rightMargin;
            }
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize
                : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int num = getChildCount();
        int lPadding = getPaddingLeft();
        int rPadding = getPaddingRight();
        int tPadding = getPaddingTop();
        int bPadding = getPaddingBottom();

        int cw = lPadding;
        int ch = tPadding;
        int right = getWidth() - rPadding;
        int bottom = getHeight() - bPadding;

        for (int i = 0; i < num; i++) {
            View c = getChildAt(i);
            MarginLayoutParams mp = (MarginLayoutParams) c.getLayoutParams();
            if (cw + c.getMeasuredWidth() + mp.leftMargin + mp.rightMargin > right) {
                if (ch + c.getMeasuredHeight() + mp.topMargin + mp.bottomMargin > bottom)
                    break;
                cw = lPadding + mp.leftMargin + c.getMeasuredWidth();
                ch += mp.topMargin + mp.bottomMargin + c.getMeasuredHeight();
                c.layout(lPadding + mp.leftMargin, ch + mp.topMargin, cw, ch + mp.topMargin + c.getMeasuredHeight());
                cw += mp.rightMargin;
            } else {
                c.layout(cw + mp.leftMargin, ch + mp.topMargin, cw + mp.leftMargin + c.getMeasuredWidth(), ch + c.getMeasuredHeight() + mp.topMargin);
                cw = cw + mp.leftMargin + mp.rightMargin + c.getMeasuredWidth();
                Log.i("Unit", String.valueOf(c.getMeasuredHeight()));
                Log.i("Unit", String.valueOf(ch + c.getMeasuredHeight() + mp.topMargin + mp.bottomMargin));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setAlpha(255);
        p.setStrokeWidth(bg_border_width);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),bg_radius,bg_radius,p);

        p.setStyle(Paint.Style.FILL);
        p.setAlpha(bg_alpha);
        canvas.drawRoundRect(0,0,getWidth(),getHeight(),bg_radius,bg_radius,p);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        return new MarginLayoutParams(getContext(), attrs);

    }
}
