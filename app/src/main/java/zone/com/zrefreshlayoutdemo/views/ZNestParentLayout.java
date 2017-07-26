package zone.com.zrefreshlayoutdemo.views;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.utils.ScrollingUtil;
import zone.com.zrefreshlayout.utils.SimpleAnimatorListener;

/**
 * [2017] by Zone
 */

public class ZNestParentLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "ZNestParentLayout";
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private IFlexScroll mIFlexScroll;
    private IScrollFlex mIScroll = new FlexScroll();//滚动策略
    private int mTopViewHeightOrg;

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
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.e(TAG, "onNestedPreScroll_getScrollY:"+ mIScroll.getScrollY() );

        Log.e(TAG, String.format("scrollto_  dx:%d \tdy:%d \tcanScrollVertically 1:%b \tcanScrollVertically -1:%b " +
                        "自己:\tcanScrollVertically 1:%b \tcanScrollVertically -1:%b"
                , dx, dy, ViewCompat.canScrollVertically(target, 1), ViewCompat.canScrollVertically(target, -1),
                ViewCompat.canScrollVertically(this, 1), ViewCompat.canScrollVertically(this, -1)));

        if (dy < 0) {//下拉

            //父控件消耗的两种情况：
            //1.下啦 显示头部  (里面不能下拉了，并且方向向上，并且 头部漏出)
            if (!ScrollingUtil.canChildScrollUp(target) && mIScroll.getScrollY() > 0 && mTopViewHeightOrg == mTopViewHeight) {
                consumed[1] = dy;
                Log.e(TAG, "scrollto_ 显示头部_下拉:" + dy);
                mIScroll.scrollBy(0, dy);
//            scrollBy(0, dy);
            }
            if (!ScrollingUtil.canChildScrollUp(target) &&  mIScroll.getScrollY() <= 0) {
                consumed[1] = dy;
                Log.e(TAG, "scrollto_越过头部_下拉:" + dy);
                mIScroll.scrollBy(0, dy);//仅仅记录 未消耗
            }

        } else if (dy>0){//上拉

            if (getScrollY() < getStopHeight()) {
                if (mTopViewHeightOrg == mTopViewHeight) {
                    int length = mTopViewHeight - stopView.getMeasuredHeight() - getScrollY();
                    consumed[1] = dy < length ? dy : length;
                    Log.e(TAG, "scrollto_隐藏头部_上啦:" + dy);
                    mIScroll.scrollBy(0, consumed[1]);
                } else {//上啦 隐藏头部  和 布局调整的时候弄的
//                    consumed[1] = dy;//吃掉 但
//                    Log.e(TAG, "scrollto_头部变大_上啦:" + dy);
//                    mIScroll.scrollBy(0, dy);
                }
            }

            if (!ScrollingUtil.canChildScrollDown(target) && getScrollY() >= getStopHeight()) {
                Log.e(TAG, "scrollto_越过头部_上啦:" + dy);
                mIScroll.scrollBy(0, dy);//仅仅记录 未消耗
            }

        }

        System.out.println("scrollto_ total:" + mIScroll.getScrollY());

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


    @Override
    public void scrollTo(int x, int y) {
        System.out.println("scrollto x:" + x + "\t y:" + y);
        //防止滑动出界； 滑动多了也不怕
        if (y < 0)
            y = 0;
        if (y > getStopHeight())
            y = getStopHeight();
        super.scrollTo(x, y);
    }

    private int getStopHeight() {
        return mTopViewHeight - stopView.getMeasuredHeight();
    }

    private void upEvent() {
        //回弹
        if (mIScroll.getScrollY() < 0) {
            Log.e(TAG, "scrollto_回弹->0！");
            mIScroll.smoothScrollTo_(0);
        }

        if (mIScroll.getScrollY() > getStopHeight()) {
            Log.e(TAG, "scrollto_回弹->stopHeight！");
            mIScroll.smoothScrollTo_(getStopHeight());
        }

    }


    private View topView;
    private View contentView;
    private int mTopViewHeight;
    private View stopView;


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

    public void setEnterAlways(View v) {
        stopView = v;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (topView != null) {
            topView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            System.out.println("顶部高度：" + topView.getMeasuredHeight());
            ViewGroup.LayoutParams params = contentView.getLayoutParams();
//            stopView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            params.height = getMeasuredHeight() - stopView.getMeasuredHeight();
            //设置要的高度 正好是 top的高度 加上 原来本身的高度；
            setMeasuredDimension(getMeasuredWidth(), topView.getMeasuredHeight() + getMeasuredHeight());
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = topView.getMeasuredHeight();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //拦截 则不会走  nest嵌套
        Log.i(TAG, "onInterceptTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTopViewHeightOrg = topView.getHeight();
                Log.i(TAG, "onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onInterceptTouchEvent ACTION_UP");
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction < 0) {
            return getScrollY() > 0;
        } else {
            return getScrollY() < mTopViewHeight;
        }
//        return super.canScrollVertically(direction);
    }


    //------------------------------------------- 滚动策略 ------------------------------------------------
    public abstract class IScrollFlex {

        protected int scrollY;
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
                        scrollToCenter(offset, true);
                    }

                });

        private boolean isRunning() {
            return valueAnimator.isRunning();
        }

        private void cancel() {
            valueAnimator.cancel();
        }

        private void smoothScrollTo_(int fy) {
            valueAnimator.setIntValues(getScrollY(), fy);
            valueAnimator.start();
        }

        private void scrollTo_(int fy) {
            scrollToCenter(fy, false);
        }

        private void scrollToCenter(int fy, boolean isAnimate) {
            scrollY = fy;
            scrollTo(fy, false);
            scrollCallback();
        }

        public void scrollCallback() {
            if (mIFlexScroll != null) {
                int offsetMyself = 0, offsetOver = 0;
                if (getScrollY() < getStopHeight() && getScrollY() > 0)
                    offsetMyself = getScrollY();
                else
                    offsetOver = getScrollY();
                Log.e(TAG, "scrollto_什么鬼:" + getScrollY());
                mIFlexScroll.scroll(offsetMyself, offsetOver);
            }
        }

        /**
         * @param fy
         * @param isAnimate 是动画的话就不做滚动映射了
         */
        protected abstract void scrollTo(int fy, boolean isAnimate);

        protected abstract void scrollBy(int fx, int fy);

        private int getScrollY() {
            return scrollY;
        }
    }

    //弹性
    public class FlexScroll extends IScrollFlex {

        protected void scrollTo(int fy, boolean isAnimate) {
            ZNestParentLayout.this.scrollTo(0, fy);
        }

        @Override
        protected void scrollBy(int fx, int fy) {
            if (mIScroll.isRunning())
                mIScroll.cancel();
            scrollY += fy;
            ZNestParentLayout.this.scrollBy(0, fy);
            scrollCallback();
        }
    }


    public interface IFlexScroll {
        void scroll(int offsetMyself, int offsetOver);
    }

    public IFlexScroll getIFlexScroll() {
        return mIFlexScroll;
    }

    public void setIFlexScroll(IFlexScroll mIFlexScroll) {
        this.mIFlexScroll = mIFlexScroll;
    }
}
