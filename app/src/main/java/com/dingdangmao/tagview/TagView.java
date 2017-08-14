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
import android.widget.TextView;

/**
 * Created by suxiaohui on 2017/8/13.
 */

public class TagView extends ViewGroup {

    private float bg_radius;
    private float bg_border_width;
    private int bg_color;
    private int bg_alpha;
    private int mleft;
    private int mright;
    private int mtop;
    private int mbottom;
    private before bf;
    private OnClickListener listener;
    private Paint p = new Paint();

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, 0);

        try {
            bg_radius = attr.getDimension(R.styleable.TagView_bg_radius, 5);
            bg_alpha = attr.getInteger(R.styleable.TagView_bg_alpha, 100);
            bg_color = attr.getColor(R.styleable.TagView_bg_color, Color.parseColor("#FFCCCC"));
            bg_border_width = attr.getFloat(R.styleable.TagView_bg_border_width, 1);

            mleft =  (int)attr.getDimension(R.styleable.TagView_mleft, 10.0f);
            mright = (int)attr.getDimension(R.styleable.TagView_mright, 10.0f);
            mtop =   (int)attr.getDimension(R.styleable.TagView_mtop, 5.0f);
            mbottom =(int)attr.getDimension(R.styleable.TagView_mbottom, 5.0f);

        } catch (Exception e) {

            Log.i("Unit","init "+e.toString());

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

            if (width + c.getMeasuredWidth() + mleft + mright > widthSize) {
                width = lPadding + rPadding + c.getMeasuredWidth() + mleft + mright;
                if (height + c.getHeight() + mtop + mbottom > heightSize)
                    break;
                height += c.getMeasuredHeight() + mtop + mbottom;
            } else {
                width += c.getMeasuredWidth() + mleft + mright;
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
            Log.i("Unit", ((TextView) c).getText().toString());

            if (cw + c.getMeasuredWidth() + mleft + mright > right) {
                if (ch + c.getMeasuredHeight() + mtop + mbottom > bottom)
                    break;
                cw = lPadding + mleft + c.getMeasuredWidth();
                ch += mtop + mbottom + c.getMeasuredHeight();
                c.layout(lPadding + mleft, ch + mtop, cw, ch + mtop + c.getMeasuredHeight());
                cw += mright;
            } else {
                c.layout(cw + mleft, ch + mtop, cw + mleft + c.getMeasuredWidth(), ch + c.getMeasuredHeight() + mtop);
                cw = cw + mleft + mright + c.getMeasuredWidth();
                //Log.i("Unit", String.valueOf(c.getMeasuredHeight()));
                //Log.i("Unit", String.valueOf(ch + c.getMeasuredHeight() + mp.topMargin + mp.bottomMargin));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setAlpha(255);
        p.setStrokeWidth(bg_border_width);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), bg_radius, bg_radius, p);

        p.setStyle(Paint.Style.FILL);
        p.setAlpha(bg_alpha);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), bg_radius, bg_radius, p);

    }
    public void addTag(String[] tag){
        if(tag.length==0)
            return ;
        for(int i=0;i<tag.length;i++) {
           addTagString(tag[i]);
        }
    }
    public void addTagString(String tag){
        if(tag==null)
            return ;
        TextView tv = new TextView(getContext(), null);
        tv.setClickable(true);
        if(listener!=null)
            tv.setOnClickListener(listener);
        tv.setText(tag);
        if(bf!=null)
            bf.execute(tv);
        addView(tv);
    }
    public void setTag(String[] tag){

        if(tag.length==0)
            return ;
        removeAllViews();
        for(int i=0;i<tag.length;i++) {
            TextView tv = new TextView(getContext(), null);
            tv.setClickable(true);
            if(listener!=null)
                tv.setOnClickListener(listener);
            tv.setText(tag[i]);
            if(bf!=null)
                bf.execute(tv);
            addView(tv);
        }
    }
    public void addListener(OnClickListener listener){
        if(listener==null)
            throw new RuntimeException("listener is null");
        this.listener=listener;
    }

    public OnClickListener getListener(){
        return listener;
    }

    public void addBefore(before bf){
        this.bf=bf;
    }

    public before getBefore(){
        return bf;
    }

    public float getBg_border_width() {
        return bg_border_width;
    }

    public void setBg_border_width(float bg_border_width) {
        this.bg_border_width = bg_border_width;
    }

    public int getBg_alpha() {
        return bg_alpha;
    }

    public void setBg_alpha(int bg_alpha) {
        this.bg_alpha = bg_alpha;
    }

    public int getBg_color() {
        return bg_color;
    }

    public void setBg_color(int bg_color) {
        this.bg_color = bg_color;
    }

    public float getBg_radius() {
        return bg_radius;
    }

    public void setBg_radius(float bg_radius) {
        this.bg_radius = bg_radius;
    }

    public int getMbottom() {
        return mbottom;
    }

    public void setMbottom(int mbottom) {
        this.mbottom = mbottom;
    }

    public int getMleft() {
        return mleft;
    }

    public void setMleft(int mleft) {
        this.mleft = mleft;
    }

    public int getMright() {
        return mright;
    }

    public void setMright(int mright) {
        this.mright = mright;
    }

    public int getMtop() {
        return mtop;
    }

    public void setMtop(int mtop) {
        this.mtop = mtop;
    }

    interface before{
        public void execute(TextView v);
    }
}
