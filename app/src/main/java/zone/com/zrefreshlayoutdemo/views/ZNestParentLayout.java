package zone.com.zrefreshlayoutdemo.views;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.utils.ScrollingUtil;
import zone.com.zrefreshlayout.utils.SimpleAnimatorListener;

/**
 * [2017] by Zone
 * <p>
 * 嵌套滑动  的onInterceptTouchEvent总是返回true 即使你false了
 * <p>
 * 而ontouch却不走
 */
public class ZNestParentLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "ZNestParentLayout";
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private IFlexScroll mIFlexScroll;
    private IScrollFlex mIScroll = new FlexScroll();//滚动策略

    public ZNestParentLayout(Context context) {
        this(context, null);
    }

    public ZNestParentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZNestParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);

    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.e(TAG, "onStopNestedScroll");
        mNestedScrollingParentHelper.onStopNestedScroll(target);
        upEvent();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onStartNestedScroll");
        mIScroll.cancel();
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//
//        Log.e(TAG, String.format("scrollto_  dx:%d \tdy:%d \tcanScrollVertically 1:%b \tcanScrollVertically -1:%b " +
//                        "自己:\tcanScrollVertically 1:%b \tcanScrollVertically -1:%b"
//                , dx, dy, ViewCompat.canScrollVertically(target, 1), ViewCompat.canScrollVertically(target, -1),
//                ViewCompat.canScrollVertically(this, 1), ViewCompat.canScrollVertically(this, -1)));

        if (!ScrollingUtil.canChildScrollUp(target)) {
            //不能 向上滚动既然 到头了
            int hei=-1;
            if(scrollOffset+dy<-maxScrollOffset){//因为向上滚动是负数
                //这里不能全部消耗
                hei=0;
                consumed[1] = -maxScrollOffset-scrollOffset;
            }else if(scrollOffset+dy>0){
                //向下滚动的时候 头部不能大于0
                hei=1;
                consumed[1] =-scrollOffset;
            } else{
                hei=2;
                //没到最大距离消耗全部
                consumed[1] = dy;
            }
            scrollOffset +=  consumed[1] ;
            Log.e(TAG, "scrollto_ 显示头部_下拉:" + dy+" \t 消耗:"+consumed[1]+"\t 步骤:"+hei);
            mIScroll.scrollBy(0, dy);
        }

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling");
        //todo  最后加
        return false;
    }


    private void upEvent() {
        //回弹
        if (scrollOffset < 0) {
            Log.e(TAG, "scrollto_回弹->0！");
            mIScroll.smoothScrollTo_(0);
        }

    }


    private View topView;
    private View contentView;
    private int maxScrollOffset = 300;
    private int scrollOffset;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        if (getChildCount() > 2)
            throw new IllegalStateException("child count must be < 2");
        if (getChildCount() == 1)
            contentView = getChildAt(0);
        if (getChildCount() == 2) {
            contentView = getChildAt(1);
            topView = getChildAt(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (topView != null) {
            topView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            System.out.println("顶部高度：" + topView.getMeasuredHeight());
            //设置要的高度 正好是 top的高度 加上 原来本身的高度；
            setMeasuredDimension(getMeasuredWidth(), topView.getMeasuredHeight() + contentView.getMeasuredHeight());
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    //------------------------------------------- 滚动策略 ------------------------------------------------
    public abstract class IScrollFlex {

        private static final int DEFAULT_DURATION = 250;

        private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofInt(0, 100)
                .setDuration(DEFAULT_DURATION)
                .setInterpolator(new DecelerateInterpolator())
                .addListener(new SimpleAnimatorListener() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.e(TAG, "onAnimationEnd");

                    }
                })
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer offset = (Integer) animation.getAnimatedValue();
                        scrollOffset=-offset;
//                        System.out.println("ZNestParentLayout addUpdateListener  offset==>"+offset);
                        scrollTo_(offset, true);
                    }

                });

        private boolean isRunning() {
            return valueAnimator.isRunning();
        }

        private void cancel() {
            valueAnimator.cancel();
        }

        private void smoothScrollTo_(int fy) {
            valueAnimator.setIntValues(-scrollOffset, fy);
            valueAnimator.start();
        }


        private void scrollTo_(int fy, boolean isAnimate) {
            scrollTo(fy, false);
            scrollCallback();
        }

        public void scrollCallback() {
            if (mIFlexScroll != null) {
                mIFlexScroll.scroll(-scrollOffset);
            }
        }

        /**
         * @param fy
         * @param isAnimate 是动画的话就不做滚动映射了
         */
        protected abstract void scrollTo(int fy, boolean isAnimate);

        protected abstract void scrollBy(int fx, int fy);

    }

    //弹性
    public class FlexScroll extends IScrollFlex {

        protected void scrollTo(int fy, boolean isAnimate) {
//            ZNestParentLayout.this.scrollTo(0, fy);
        }

        @Override
        protected void scrollBy(int fx, int fy) {
            if (mIScroll.isRunning())
                mIScroll.cancel();
//            scrollY += fy;
//            ZNestParentLayout.this.scrollBy(0, fy);
            scrollCallback();
        }
    }


    public interface IFlexScroll {
        void scroll(int offset);
    }

    public IFlexScroll getIFlexScroll() {
        return mIFlexScroll;
    }

    public void setIFlexScroll(IFlexScroll mIFlexScroll) {
        this.mIFlexScroll = mIFlexScroll;
    }
}
