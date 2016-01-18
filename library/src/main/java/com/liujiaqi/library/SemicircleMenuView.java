/*
 * Copyright 2016 CaryMax1988
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liujiaqi.library;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by CaryMax1988 on 2016/1/13.
 * EMail liujiaqi19881124@gmail.com
 */
public class SemicircleMenuView extends ViewGroup{

    public enum AnimationType{
        ROTATE,
        ZOOM,
        ALPHA,
        NONE
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    //默认问旋转动画
    private AnimationType animationType = AnimationType.NONE;

    //上下文
    private Context mContext = null;
    //圆环半径
    private float mIntersize = 0;
    //圆环半径默认值
    private final float mDefaultInterSize = 50;

    //孩子控件大小
    private float mInRectSize = 0;
    private float mRectMarge = 0;
    private float mDefaultRectMarge = 10;

    //圆心坐标
    private float mCircleRadiusX = 0;
    private float mCircleRadiusY = 0;
    //外圆半径
    private float mCircleRadius = 0;
    //内圆半径
    private float mInterCircleRadius = 0;
    //阴影
    private float mElevation = 0;

    private int position = 0;

    public SemicircleMenuView(Context context) {
        this(context, null);
    }

    public SemicircleMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemicircleMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.semicirclemenu);
        final int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        mIntersize = a.getDimension(R.styleable.semicirclemenu_intersize, mDefaultInterSize);
        mElevation = a.getDimension(R.styleable.semicirclemenu_outelevation, 0) > 3
                ? a.getDimension(R.styleable.semicirclemenu_outelevation, 0) : 3;
        mRectMarge = a.getDimension(R.styleable.semicirclemenu_rectmarge, mDefaultRectMarge);
        a.recycle();
    }

    /**
     * Measure
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getDefaultSize(0, widthMeasureSpec);
        int h = getDefaultSize(0, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(w, h);

        //计算圆心点x
        mCircleRadiusX = w/2;
        //计算半径大小
        if(h < w/2){
            if(h !=0)

            mCircleRadius = (float) ((Math.pow(w, 2) + 4*Math.pow(h, 2))/(8*h));
            //计算圆心点y
            mCircleRadiusY = mCircleRadius;
        }
        else{
            mCircleRadius = w/2;
            //计算圆心点y
            mCircleRadiusY = h;
        }
        //计算内圆半径
        mInterCircleRadius = mCircleRadius - mIntersize;

        setMeasuredChild();
    }

    private void setMeasuredChild() {
        mInRectSize = (float) Math.sqrt(Math.pow(mIntersize, 2)/2);
        int realSize = (int) (mInRectSize-mRectMarge);
        int cCount = getChildCount();
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++)
        {
            View child = getChildAt(i);
            int childSize = getDefaultChildSize(child);
            //如果控件过大的话
            if(childSize > realSize){
                child.measure(realSize, realSize);
            }
        }

    }

    private int getDefaultChildSize(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        return  w>h ? w : h;
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * Layout
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        if(cCount == 0){
            return;
        }
        int width  = getWidth();
        int height = getHeight();

        //计算圆半径
        float c_ir = mCircleRadius-mIntersize/2;
        float cent_width = (width - mIntersize)/(cCount+1);
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++)
        {
            position = i;
            //计算孩子中心的x轴坐标点
            float tempx = mIntersize/2+(i+1)*cent_width;
            //计算直角三角形的高度
            float tempheight = (float) Math.sqrt(Math.abs(((c_ir*c_ir)-(Math.abs(width/2-tempx))*(Math.abs(width/2-tempx)))));
            //计算孩子中心的y轴坐标点
            float tempy;
            if(height >= mCircleRadius)
            {
                tempy = height - tempheight;
            }
            else{
                tempy = mCircleRadius - tempheight;
            }
            int size = getDefaultChildSize(getChildAt(i));
            int childleft = (int)(tempx) - size/2;
            int childtop = (int)(tempy) - size/2;
            int childright = (int)(tempx) + size/2;
            int childbottom = (int)(tempy) + size/2;
            getChildAt(i).layout(childleft, childtop, childright, childbottom);
            final View currentView = getChildAt(i);
            //将孩子旋转到指定角度
            AnimatorSet set = new AnimatorSet() ;
            float rote;
            //计算旋转角度
            float rotateSize = (float) Math.toDegrees(Math.atan(cent_width / (height - tempy)));
            if(cCount % 2 == 0){
                //偶数个
                rote = (i < cCount/2) ? -rotateSize : rotateSize;
            }
            else{
                //奇数个
                rote = (i < Math.floor(cCount/2))
                        ? -rotateSize
                        : (i == Math.floor(cCount/2)) ? 0 : rotateSize;
            }
            ObjectAnimator anim1 = ObjectAnimator .ofFloat(getChildAt(i), "rotation", 0f, rote);
            anim1.setDuration(10);
            set.play(anim1);
            set.start();

            switch (animationType){
                case ROTATE:
                {
                    //旋转动画
                    Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
                    DecelerateInterpolator lin = new DecelerateInterpolator();//减速旋转
                    operatingAnim.setInterpolator(lin);
                    getChildAt(i).startAnimation(operatingAnim);
                }
                break;
                case ZOOM:
                {
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(getChildAt(i), "alpha", 0f, 1f);
                    alpha.setDuration(2000);//设置动画时间
                    alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
                    alpha.setRepeatCount(0);//设置动画重复次数，这里-1代表无限
                    //缩放动画
                    AnimatorSet animatorSet = new AnimatorSet();//组合动画
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(getChildAt(i), "scaleX", 2f, 1f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(getChildAt(i), "scaleY", 2f, 1f);
                    animatorSet.setDuration(2000);
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                    animatorSet.play(scaleX).with(scaleY).with(alpha);//两个动画同时开始
                    animatorSet.start();
                }
                break;
                case ALPHA:
                {
                    //渐变动画
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(getChildAt(i), "alpha", 0f, 1f);
                    alpha.setDuration(2000);//设置动画时间
                    alpha.setInterpolator(new DecelerateInterpolator());//设置动画插入器，减速
                    alpha.setRepeatCount(0);//设置动画重复次数，这里-1代表无限
                    alpha.start();
                }
                break;
                case NONE:
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        Path mPath = new Path();

        mPath.addCircle(mCircleRadiusX, mCircleRadiusY, mCircleRadius, Path.Direction.CW);
        canvas.clipPath(mPath, Region.Op.REPLACE);

        mPath.reset();
        mPath.addCircle(mCircleRadiusX, mCircleRadiusY, mInterCircleRadius, Path.Direction.CW);
        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        super.draw(canvas);
    }
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
