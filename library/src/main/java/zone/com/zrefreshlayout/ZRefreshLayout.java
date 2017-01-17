package zone.com.zrefreshlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.footer.LoadFooter;
import zone.com.zrefreshlayout.header.SinaRefreshHeader;
import zone.com.zrefreshlayout.utils.ScrollingUtil;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

/**
 * Created by fuzhipeng on 2017/1/9.
 */

//我这个可以 ,立即下拉不等Rest
//自动刷新；-->  直接到 头部位置 并且是 刷新状态
//demo listView，GridView recycler imageView scrollerView,WebView
//先弄上啦加载 最后是否固定头部 已经固定底部   下拉头部高度   仅头部使用
//阻力下拉
//全局切换  头与脚,全局配置初始化
//自定义头部 与 底部；  新浪 google支持, wave映射图
//todo ReadMe,wiki  名字改动
//todo 拦截自定义可滑动view ,viewPager 横向兼容

public class ZRefreshLayout extends FrameLayout {

    static Config config;

    private static final int REST = 0;//等待状态-休息；
    private static final int PULL = 1;//下拉
    private static final int REFRESH_ABLE = 2;//松开刷新 条件--->滑动超过头部高度
    private static final int REFRESHING = 3;//刷新中
    private static final int COMPLETE = 4;//松开刷新
    private static final int LOADMORE_ING = 5;//加载中
    private static final int AUTO_PULL = 6;//自动刷新

    private int state = REST;

    private int mTouchSlop;//滑动有效的最小距离
    private View headerView, footerView, content;
    private IHeaderView mIHeaderView;
    private IFooterView mIFooterView;
    private boolean isPinContent;//头部是否固定

    private IScroll mIScroll;//滚动策略
    private PullListener mPullListener;
    private RefreshAbleListener mRefreshAbleListener;
    private LoadMoreListener mLoadMoreListener;
    private IResistance mIResistance;//阻力策略
    AnimateBack mAnimateBack = AnimateBack.None;

    public ZRefreshLayout(Context context) {
        this(context, null);
    }

    public ZRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initPinContent();
        initHeaderView();
        initFooterView();
        initResistance();
    }

    private void initResistance() {
        if (config != null && config.resistance != null)
            mIResistance = config.resistance.clone_();
    }

    private void initPinContent() {
        if (config != null && config.isPinContent)
            mIScroll = new Scroll_Pin();
        else
            mIScroll = new Scroll_PinNot();
    }

    private void initHeaderView() {
        if (mIHeaderView != null)//个人配置
            headerView = mIHeaderView.getView(this);
        else {
            if (config != null && config.headerView != null) {
                //全局配置
                mIHeaderView = config.headerView.clone_();
                if (mIHeaderView != null)
                    headerView = mIHeaderView.getView(this);
            }
            //全局配置clone为空的时候
            if (mIHeaderView == null) {
                //都为空 默认新浪
                mIHeaderView = new SinaRefreshHeader();
                headerView = mIHeaderView.getView(this);
            }
        }
    }

    private void initFooterView() {
        if (mIFooterView != null)//个人配置
            footerView = mIFooterView.getView(this);
        else {
            if (config != null && config.footerView != null) {
                //全局配置
                mIFooterView = config.footerView.clone_();
                if (mIFooterView != null)
                    footerView = mIFooterView.getView(this);
            }
            //全局配置clone为空的时候
            if (footerView == null) {
                //都为空 默认新浪
                mIFooterView = new LoadFooter();
                footerView = mIFooterView.getView(this);
            }
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(headerView);
        addView(footerView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        headerView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        if (mLoadMoreListener != null)
            footerView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        headerView.layout(left, -headerView.getHeight(), right, 0);
        if (mLoadMoreListener != null)
            footerView.layout(left, bottom - footerView.getHeight(), right, bottom);
        content = getChildAt(0);
    }

    private float mTouchX, mTouchY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = event.getX();
                mTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - mTouchX;
                float dy = event.getY() - mTouchY;
                log("mTouchY :" + mTouchY + "___  event.getY():" + event.getY());
                log("Math.abs(dy) :" + Math.abs(dy) + "___ mTouchSlop:" + mTouchSlop);
                log("Math.abs(dx) <= Math.abs(dy)p:" + (Math.abs(dx) <= Math.abs(dy)));
                log("Math.abs(dy) > mTouchSlop:" + (Math.abs(dy) > mTouchSlop));
                log("!ScrollingUtil.canChildScrollUp(content):" + !ScrollingUtil.canChildScrollUp(content));
                if (dy > mTouchSlop && Math.abs(dx) <= Math.abs(dy)
                        && !ScrollingUtil.canChildScrollUp(content)) {
                    //滑动允许最大角度为45度
                    log("下拉截断！");
                    return true;
                }
                if (mLoadMoreListener != null && dy < 0
                        && Math.abs(dy) > mTouchSlop
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

    private int direction = 0;
    private final static int LOAD_UP = 1;
    private final static int PULL_DOWN = 2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {//被拦截状态下 不是刷新就是 加载更多

        if (state == AUTO_PULL) return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                if (dy > 0)
                    direction = PULL_DOWN;
                else
                    direction = LOAD_UP;

                if (mIResistance != null)//dy 需要映射不然 真实的移动 和 MotionEvent移动对不上了 也没法和headerView.getHeight()[真实的]比较了
                    dy = mIResistance.getOffSetYMapValue(headerView.getHeight(), (int) dy);

                if (state == REST && direction == PULL_DOWN) {
                    state = PULL;
                    log("PULL！");
                }

                if (mLoadMoreListener != null && Math.abs(dy) > mTouchSlop && state == REST && direction == LOAD_UP && mIScroll.getScrollY() == 0) {
                    state = LOADMORE_ING;
                    log("LoadMore！dy:" + dy + "_ mTouchY:" + mTouchY + "__event.getY():" + event.getY());
                    mLoadMoreListener.loadMore(this);
                    if (mIFooterView != null)
                        mIFooterView.onStart(footerView.getHeight());

                }

                if (direction == PULL_DOWN && state == PULL && Math.abs(dy) >= getRefreshAbleHeight()) {
                    state = REFRESH_ABLE;
                    log("Math.abs(dy):" + Math.abs(dy) + "___getRefreshAbleHeight():" + getRefreshAbleHeight());
                    log("REFRESH_ABLE！");
                    mIHeaderView.refreshAble(true);
                    if (mRefreshAbleListener != null)
                        mRefreshAbleListener.refreshAble(true);
                }
                if (state == REFRESH_ABLE && Math.abs(dy) < getRefreshAbleHeight()) {
                    state = PULL;
                    log("Math.abs(dy):" + Math.abs(dy) + "___getRefreshAbleHeight():" + getRefreshAbleHeight());
                    log("PULL！");
                    mIHeaderView.refreshAble(false);
                    if (mRefreshAbleListener != null)
                        mRefreshAbleListener.refreshAble(false);
                }

                if (state == PULL || state == REFRESH_ABLE) {
                    //防止别的状态 可以滚动
                    mIScroll.scrollTo_((int) (event.getY() - mTouchY));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (state == PULL) {
                    //回弹
                    mAnimateBack = AnimateBack.DisRefreshAble_Back;
                    mIScroll.smoothScrollTo_(0);
                    log("回弹！");
                }
                if (state == REFRESH_ABLE) {
                    //刷新
                    state = REFRESHING;
                    log("释放回弹 将要刷新");
                    mAnimateBack = AnimateBack.RefreshAble_Back;
                    mIScroll.smoothScrollTo_(getRefreshAbleHeight());
                    //等待回调  刷新完成
                    if (mPullListener != null)
                        mPullListener.refresh(this);
                    if (mIHeaderView != null)
                        mIHeaderView.onRefreshing(headerView.getHeight(), false);
                }

                break;
            default:
                break;
        }
        return true;
    }

    int heightToRefresh;

    //因为头部设置更有效 所以就暴露了  通过AUtils类暴露接口
    void setHeaderHeightToRefresh(int heightToRefresh) {
        this.heightToRefresh = heightToRefresh;
    }

    private int getRefreshAbleHeight() {
        return heightToRefresh != 0 ? heightToRefresh : headerView.getHeight();
    }

    public void autoRefresh() {
        if (state == REST) {
            mIScroll.scrollTo_(getRefreshAbleHeight(), true);
            //等待回调  刷新完成
            state = AUTO_PULL;
            log("AUTO_PULL！");
            if (mPullListener != null)
                mPullListener.refresh(this);
            if (mIHeaderView != null) {
                mIHeaderView.onRefreshing(headerView.getHeight(), true);
            }
        }
    }

    public void refreshComplete() {
        mAnimateBack = AnimateBack.Complete_Back;
        mIScroll.smoothScrollTo_(0);
    }

    public void loadMoreComplete() {
        mIFooterView.onComplete();
        mLoadMoreListener.complete(this);
        state = REST;
    }

    public LoadMoreListener getLoadMoreListener() {
        return mLoadMoreListener;
    }

    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
        if (state == REST)
            requestLayout();
    }

    public PullListener getPullListener() {
        return mPullListener;
    }

    public void setPullListener(PullListener mPullListener) {
        this.mPullListener = mPullListener;
    }

    public IHeaderView getIHeaderView() {
        return mIHeaderView;
    }

    public void setIHeaderView(@NonNull IHeaderView mIHeaderView) {
        this.mIHeaderView = mIHeaderView;
        removeView(headerView);
        headerView = mIHeaderView.getView(this);
        addView(headerView);
    }

    public IFooterView getIFooterView() {
        return mIFooterView;
    }

    public void setIFooterView(@NonNull IFooterView mIFooterView) {
        this.mIFooterView = mIFooterView;
        removeView(footerView);
        footerView = mIFooterView.getView(this);
        addView(footerView);
    }

    public void setPinContent(boolean isPinContent) {
        this.isPinContent = isPinContent;
        if (state == REST)
            if (this.isPinContent)
                mIScroll = new Scroll_Pin();
            else
                mIScroll = new Scroll_PinNot();
    }

    public boolean isPinContent() {
        return isPinContent;
    }

    public IResistance getIResistance() {
        return mIResistance;
    }

    public void setIResistance(IResistance mIResistance) {
        this.mIResistance = mIResistance;
    }

    //------------------------------------------- 滚动策略 ------------------------------------------------
    public abstract class IScroll {

        private static final int DEFAULT_DURATION = 250;
        private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofInt(0, 100)
                .setDuration(DEFAULT_DURATION)
                .setInterpolator(new LinearInterpolator())
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer offset = (Integer) animation.getAnimatedValue();
                        if (mIHeaderView != null)
                            mIHeaderView.animateBack(mAnimateBack,
                                    1F * Math.abs(getScrollY()) / headerView.getHeight(),
                                    headerView.getHeight(), isPinContent);
                        scrollTo(offset, true);
                        refreshCompeleStateToRest(offset);
                    }

                });

        private void refreshCompeleStateToRest(Integer offset) {
            if (offset == 0 && (state == REFRESHING || state == PULL || state == AUTO_PULL)) {
                log("刷新结束， 回滚到0");
                if (mPullListener != null && state == REFRESHING) {
                    mPullListener.complete(ZRefreshLayout.this);
                }
                state = COMPLETE;
                log("complete");
                if (mIHeaderView != null)
                    mIHeaderView.onComplete();
                state = REST;
                mAnimateBack = AnimateBack.None;
            }
        }

        private void smoothScrollTo_(int fy) {
            if (mIHeaderView.interceptAnimateBack(mAnimateBack, mIScroll))
                return;
            valueAnimator.setIntValues(getScrollY(), fy);
            valueAnimator.start();
        }

        void smoothScrollTo_NotIntercept(int fy) {
            valueAnimator.setIntValues(getScrollY(), fy);
            valueAnimator.start();
        }

        private void scrollTo_(int fy) {
            scrollTo(fy > 0 ? fy : 0, false);
        }

        private void scrollTo_(int fy, boolean isAnimate) {
            scrollTo(fy > 0 ? fy : 0, isAnimate);
        }

        /**
         * @param fy
         * @param isAnimate 是动画的话就不做滚动映射了
         */
        protected abstract void scrollTo(int fy, boolean isAnimate);

        protected abstract int getScrollY();
    }

    public class Scroll_Pin extends IScroll {

        @Override
        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIResistance != null && !isAnimate)
                fy = mIResistance.getOffSetYMapValue(headerView.getHeight(), fy);
            if (mIHeaderView != null && !isAnimate)
                mIHeaderView.onPullingDown(1F * Math.abs(fy) / headerView.getHeight(), headerView.getHeight());
            headerView.setTranslationY(fy);
        }

        @Override
        protected int getScrollY() {
            int scrolly = (int) headerView.getTranslationY();
            return scrolly;
        }
    }

    public class Scroll_PinNot extends IScroll {

        @Override
        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIResistance != null && !isAnimate)
                fy = mIResistance.getOffSetYMapValue(headerView.getHeight(), fy);
            if (mIHeaderView != null && !isAnimate)
                mIHeaderView.onPullingDown(1F * Math.abs(fy) / headerView.getHeight(), headerView.getHeight());
            ZRefreshLayout.this.scrollTo(0, -fy);
        }

        @Override
        protected int getScrollY() {
            int scrolly = -ZRefreshLayout.this.getScrollY();
            return scrolly;
        }
    }

    public RefreshAbleListener getRefreshAbleListener() {
        return mRefreshAbleListener;
    }

    public void setRefreshAbleListener(RefreshAbleListener mRefreshAbleListener) {
        this.mRefreshAbleListener = mRefreshAbleListener;
    }

    //-------------------------内部接口----------------------------------------------------
    public interface PullListener {
        void refresh(ZRefreshLayout zRefreshLayout);

        void complete(ZRefreshLayout zRefreshLayout);
    }

    public interface RefreshAbleListener {
        void refreshAble(boolean refreshAble);
    }

    public interface LoadMoreListener {
        void loadMore(ZRefreshLayout zRefreshLayout);

        void complete(ZRefreshLayout zRefreshLayout);
    }
}
