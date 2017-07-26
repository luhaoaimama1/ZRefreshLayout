package zone.com.zrefreshlayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.utils.ScrollingUtil;
import zone.com.zrefreshlayout.utils.SimpleAnimatorListener;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

/**
 * Created by fuzhipeng on 2017/1/9.
 */
public class ZFlexibilityLayout extends LinearLayout {

    private int mTouchSlop;//滑动有效的最小距离
    private IScrollFlex mIScroll = new FlexScroll();//滚动策略
    private IResistanceFlex mIResistance;//阻力策略
    private IFlexScroll mIFlexScroll;
    private View content, headerView;
    private int headerHeightOrg, headerHeight, contentHeight, windowOffset;

    private Type type;

    public enum Type {
        Scroll, Header, Scale;
    }

    public ZFlexibilityLayout(Context context) {
        this(context, null);
    }

    public ZFlexibilityLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZFlexibilityLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setOrientation(VERTICAL);
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = new View(getContext());
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));
        addView(view, 0);//add一个header View用来站位
        content = getChildAt(0);
        if (getChildCount() > 2)
            throw new IllegalStateException("child count must be < 2");
        if (getOrientation() == HORIZONTAL)
            throw new IllegalStateException("HORIZONTAL is not support");
        if (getChildCount() == 1)
            content = getChildAt(0);
        if (getChildCount() == 2) {
            content = getChildAt(1);
            headerView = getChildAt(0);
        }
    }


    private float mTouchX, mTouchY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                headerHeightOrg = headerView.getHeight();
                mTouchX = event.getX();
                mTouchY = event.getY();
                log("down!事件");
                log("ACTION_DOWN:onInterceptTouchEvent_ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - mTouchX;
                float dy = event.getY() - mTouchY;
                log("mTouchY :" + mTouchY + "___  event.getY():" + event.getY());
                log("dy :" + dy + "___ mTouchSlop:" + mTouchSlop);

                if (dy > mTouchSlop && Math.abs(dx) <= Math.abs(dy)
                        && !ScrollingUtil.canChildScrollUp(content)) {
                    //滑动允许最大角度为45度
                    log("下拉截断！");
                    return true;
                }

                if (dy < 0 && Math.abs(dy) > mTouchSlop
                        && Math.abs(dx) <= Math.abs(dy)
                        && !ScrollingUtil.canChildScrollDown(content)) {
                    log("上啦截断！");
                    return true;
                }

                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }


    private int accumulate;
    private int dy;
    private int eventIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {//被拦截状态下 不是刷新就是 加载更多

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                log("ACTION_DOWN:ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                eventIndex = event.getActionIndex();
                log("ACTION_DOWN:id_eventIndex:" + eventIndex + "\t id:" + event.getPointerId(eventIndex));
                log("ACTION_DOWN:ACTION_POINTER_DOWN_0:" + event.toString());
                accumulate += dy;
                dy = 0;
                mTouchY = event.getY(eventIndex);
                break;
            case MotionEvent.ACTION_MOVE:
//                log("ACTION_DOWN:ACTION_MOVE:" + event.toString());
                log("ACTION:ACTION_MOVE");
                dy = (int) (event.getY(eventIndex) - mTouchY);
                int dyMove = dy + accumulate+windowOffset;//todo 修正 布局导致的偏差 所以修正回来;
                if (mIResistance != null)//dy 需要映射不然 真实的移动 和 MotionEvent移动对不上了 也没法和getRefreshAbleHeight()[真实的]比较了
                    dyMove = mIResistance.getOffSetYMapValue(contentHeight, headerHeight, dyMove);
                move(dyMove);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                log("ACTION_DOWN:id_eventIndex:" + event.getActionIndex() + "\t id:" + event.getPointerId(event.getActionIndex()));
                int pointerSize = event.getPointerCount();
                int removeIndex = event.getActionIndex();
                eventIndex = pointerSize - 2;//从0开始
                if (removeIndex != pointerSize - 1)
                    mTouchY = event.getY(pointerSize - 1);
                else
                    mTouchY = event.getY(pointerSize - 2);
                accumulate += dy;
                dy = 0;
                log("ACTION_DOWN:ACTION_POINTER_UP:" + event.toString());
                break;
            case MotionEvent.ACTION_UP:
                log("ACTION:ACTION_UP！");
            case MotionEvent.ACTION_CANCEL:
                log("ACTION:ACTION_CANCEL！");
                upEvent();
                accumulate = 0;
                eventIndex = 0;
                break;
            default:
                log("ACTION_DOWN:" + event.getAction());
                break;
        }
        return true;
    }

    private void upEvent() {
        //回弹
        mIScroll.smoothScrollTo_(0);
        log("回弹！");
    }

    //dy > 0 pull
    private void move(float dy) {
        //防止别的状态 可以滚动
        log("dy:" + dy);
        if (mIScroll.isRunning())
            mIScroll.cancel();
        mIScroll.scrollTo_((int) dy);
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
                        log("onAnimationEnd");

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
            //            if (mIResistance != null && !isAnimate)
//                fy = mIResistance.getOffSetYMapValue(0, fy);
            scrollY = fy;
            if (mIFlexScroll != null)
                mIFlexScroll.scroll(contentHeight, headerHeight, fy);
            scrollTo(fy, false);
        }

        /**
         * @param fy
         * @param isAnimate 是动画的话就不做滚动映射了
         */
        protected abstract void scrollTo(int fy, boolean isAnimate);

        private int getScrollY() {
            return scrollY;
        }
    }

    //弹性
    public class FlexScroll extends IScrollFlex {

        protected void scrollTo(int fy, boolean isAnimate) {
            ZFlexibilityLayout.this.scrollTo(0, -fy);
        }
    }

    //头部变大
    public class FlexHeader extends IScrollFlex {

        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIFlexScroll != null) {
                ViewGroup.LayoutParams lp = headerView.getLayoutParams();
                lp.height=fy+headerHeightOrg;
                headerView.setLayoutParams(lp);
                mIFlexScroll.scroll(contentHeight, headerHeight, fy);
            }

        }
    }

    //    小米阻尼
    public class FlexScale extends IScrollFlex {

        protected void scrollTo(int fy, boolean isAnimate) {
//            if (headerView == null)
//                throw new IllegalStateException("child must be twos");
            if (fy >= 0) {
                ViewCompat.setPivotY(content, 0);
                ViewCompat.setScaleY(content, (contentHeight + fy) * 1F / contentHeight);
            } else {
                ViewCompat.setPivotY(content, contentHeight);
                ViewCompat.setScaleY(content, (contentHeight - fy) * 1F / contentHeight);
            }
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (headerView != null)
            headerHeight = headerView.getHeight();
        if (content != null)
            contentHeight = content.getHeight();
        if (headerHeightOrg != 0)
            windowOffset = headerHeightOrg - contentHeight;
    }

    public interface IResistanceFlex {
        /**
         * @param headerHeight
         * @param offset       是>0的值
         * @return
         */
        int getOffSetYMapValue(int contentHeight, int headerHeight, int offset);
    }

    public interface IFlexScroll {
        /**
         * @param headerHeight
         * @param offset       是>0的值
         * @return
         */
        void scroll(int contentHeight, int headerHeight, int offset);
    }


    public IResistanceFlex getIResistance() {
        return mIResistance;
    }

    public void setIResistance(IResistanceFlex mIResistance) {
        this.mIResistance = mIResistance;
    }

    public IScrollFlex getIScroll() {
        return mIScroll;
    }

    public void setIScroll(IScrollFlex mIScroll) {
        this.mIScroll = mIScroll;
    }

    public IFlexScroll getIFlexScroll() {
        return mIFlexScroll;
    }

    public void setIFlexScroll(IFlexScroll mIFlexScroll) {
        this.mIFlexScroll = mIFlexScroll;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        switch (type) {
            case Header:
                this.mIScroll = new FlexHeader();
                break;
            case Scroll:
                this.mIScroll = new FlexScroll();
                break;
            case Scale:
                this.mIScroll = new FlexScale();
                break;
        }
    }
}
