package zone.com.zrefreshlayout;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;

import zone.com.zrefreshlayout.footer.LoadFooter;
import zone.com.zrefreshlayout.header.SinaRefreshHeader;
import zone.com.zrefreshlayout.loadmore.LoadMoreController;
import zone.com.zrefreshlayout.loadmore.LoadMoreOtherListener;
import zone.com.zrefreshlayout.scroll.ScrollDoNothing;
import zone.com.zrefreshlayout.scroll.ScrollPin;
import zone.com.zrefreshlayout.scroll.ScrollPinNot;
import zone.com.zrefreshlayout.utils.ScrollingUtil;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

/**
 * Created by fuzhipeng on 2017/1/9.
 */

//自动刷新；-->  直接到 头部位置 并且是 刷新状态
//demo listView，GridView recycler imageView scrollerView,WebView
//先弄上啦加载 最后是否固定头部 已经固定底部   下拉头部高度   仅头部使用
//阻力下拉
//全局切换  头与脚,全局配置初始化
//自定义头部 与 底部；  新浪 google支持, wave映射图
//ReadMe,wiki  名字改动
public class ZRefreshLayout extends NestFrameLayout {

    public enum HeadPin {
        PIN, NOT_PIN, NOTHING
    }
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    static Config config;

    public static final int REST = 0;//等待状态-休息；
    public static final int PULL = 1;//下拉
    public static final int REFRESH_ABLE = 2;//松开刷新 条件--->滑动超过头部高度
    public static final int REFRESHING = 3;//刷新中
    public static final int COMPLETE = 4;//松开刷新
    public static final int LOADMORE_ING = 5;//加载中
    public static final int AUTO_PULL = 6;//自动刷新

    int state = REST;

    private int mTouchSlop;//滑动有效的最小距离
    protected View headerView, footerView, contentView;
    protected int contentHeight;
    protected IHeaderView mIHeaderView;
    protected IFooterView mIFooterView;
    private HeadPin mHeadPin;//头部是否固定

    protected IScroll mIScroll;//滚动策略
    protected List<IScroll> mIScrollList;
    protected PullListener mPullListener;
    protected PullStateRestListener mPullStateRestListener;
    protected RefreshAbleListener mRefreshAbleListener;
    protected LoadMoreListener mLoadMoreListener;
    protected LoadMoreStateRestListener mLoadMoreStateRestListener;
    protected IResistance mIResistance;//阻力策略
    ScrollAnimation mScrollAnimation = ScrollAnimation.None;
    private boolean isCanLoadMore = true;
    private boolean isCanRefresh = true;
    //ms 设置最长时间 自动恢复; 默认时间config里的配置  -1关闭延迟功能
    private int delay_millis_auto_complete;

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
        initDelayAutoComplete();
        initPinContent();
        initHeaderView();
        initFooterView();
        initResistance();
        initListener();
    }

    private void initDelayAutoComplete() {
        if (config != null)
            delay_millis_auto_complete = config.delay_millis_auto_complete;
    }

    private void initListener() {
        if (config != null && config.mPullListener != null)
            mPullListener = config.mPullListener;
        if (config != null && config.mPullStateRestListener != null)
            mPullStateRestListener = config.mPullStateRestListener;
        if (config != null && config.mLoadMoreListener != null)
            mLoadMoreListener = config.mLoadMoreListener;
        if (config != null && config.mLoadMoreStateRestListener != null)
            mLoadMoreStateRestListener = config.mLoadMoreStateRestListener;
    }

    private void initResistance() {
        if (config != null && config.resistance != null)
            mIResistance = config.resistance.clone_();
    }

    private void initPinContent() {
        if (config != null && config.headPin != null) {
            generateHeadPin(config.headPin);
        } else {
            mIScroll = new ScrollPin(this);
            this.mHeadPin = HeadPin.PIN;
        }
    }

    private void generateHeadPin(HeadPin headPin) {
        switch (headPin) {
            case PIN:
                mIScroll = new ScrollPin(this);
                this.mHeadPin=HeadPin.PIN;
                break;
            case NOT_PIN:
                mIScroll = new ScrollPinNot(this);
                this.mHeadPin=HeadPin.NOT_PIN;
                break;
            case NOTHING:
                mIScroll = new ScrollDoNothing(this);
                this.mHeadPin=HeadPin.NOTHING;
                break;
        }
    }

    protected void initHeaderView() {
        if (mIHeaderView != null)//个人配置
            headerView = mIHeaderView.initView(this);
        else {
            if (config != null && config.headerView != null) {
                //全局配置
                mIHeaderView = config.headerView.clone_();
                if (mIHeaderView != null)
                    headerView = mIHeaderView.initView(this);
            }
            //全局配置clone为空的时候
            if (mIHeaderView == null) {
                //都为空 默认新浪
                mIHeaderView = new SinaRefreshHeader();
                headerView = mIHeaderView.initView(this);
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
        contentView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (contentView != null)
            contentHeight = contentView.getHeight();
        LayoutParams headerViewLp = (LayoutParams) headerView.getLayoutParams();
        //headerView 仅仅支持 bottomMargin
        headerView.layout((getWidth() - headerView.getWidth()) / 2, -headerView.getHeight() - headerViewLp.bottomMargin, (getWidth() + headerView.getWidth()) / 2, -headerViewLp.bottomMargin);
        if (mLoadMoreListener != null) {
            log("bottom - footerView.getHeight():" + (bottom - footerView.getHeight()) + "\t bottom:" + bottom);
            footerView.layout(0, getHeight() - footerView.getHeight(), getWidth(), getHeight());
        }
    }

    private float mTouchX, mTouchY;

    //防止down事件被吃 就是刷新完事 手指不离开继续下滑的问题;

    boolean haveDownEvent = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        log("onInterceptTouchEvent:" + event.getY());
        boolean returnValue = false;
        if (state == REST) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchX = event.getX();
                    mTouchY = event.getY();
                    log("down!事件");
                    haveDownEvent = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (haveDownEvent) {
                        float dx = event.getX() - mTouchX;
                        float dy = event.getY() - mTouchY;
                        log("mTouchY :" + mTouchY + "___  event.getY():" + event.getY());
                        log("dy :" + dy + "___ mTouchSlop:" + mTouchSlop);

//                    log(String.format("下拉截断判断:%b\tdy>mTouchSlop:%b \tMath.abs(dx) <= Math.abs(dy):%b\t !ScrollingUtil.canChildScrollUp(contentView):%b",
//                            dy > mTouchSlop && Math.abs(dx) <= Math.abs(dy)&& !ScrollingUtil.canChildScrollUp(contentView)
//                            ,dy>mTouchSlop,Math.abs(dx) <= Math.abs(dy),!ScrollingUtil.canChildScrollUp(contentView)));
                        if (isCanRefresh && dy > mTouchSlop && Math.abs(dx) <= Math.abs(dy)
                                && !ScrollingUtil.canChildScrollUp(contentView)) {
                            //滑动允许最大角度为45度
                            log("下拉截断！");
                            returnValue = true;
                        }

                        if (!isInterceptInnerLoadMore && isCanLoadMore && mLoadMoreListener != null && dy < 0
                                && Math.abs(dy) > mTouchSlop
                                && Math.abs(dx) <= Math.abs(dy)
                                && !ScrollingUtil.canChildScrollDown(contentView)) {
                            log("上啦截断！");
                            returnValue = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        //mNestedScrollInProgress：true 代表 child支持嵌套滑动 就不需要拦截了。如果false则进行拦截
        if (mNestedScrollInProgress) {
            return false;
        } else {
            if (!returnValue)
                returnValue = super.onInterceptTouchEvent(event);
            return returnValue;
        }
    }

    protected int direction = 0;
    protected final static int LOAD_UP = 1;
    protected final static int PULL_DOWN = 2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {//被拦截状态下 不是刷新就是 加载更多

        if (state == AUTO_PULL) return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                log("dy:" + dy + "_ mTouchY:" + mTouchY + "__event.getY():" + event.getY());
                realMove(dy);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                finishSpinner();
                break;
            default:
                break;
        }
        return true;
    }

    protected void realMove(float dy) {
        if (dy > 0) direction = PULL_DOWN;
        else direction = LOAD_UP;

        //dy 需要映射不然 真实的移动 和 MotionEvent移动对不上了 也没法和getRefreshAbleHeight()[真实的]比较了
        if (mIResistance != null)
            dy = mIResistance.getOffSetYMapValue(getRefreshAbleHeight(), (int) dy);

        if (isCanRefresh && state == REST && direction == PULL_DOWN) {
            state = PULL;
            log("PULL！");
        }

        if (!isInterceptInnerLoadMore && haveDownEvent && isCanLoadMore && mLoadMoreListener != null && Math.abs(dy) > mTouchSlop && state == REST && direction == LOAD_UP && mIScroll.getScrollY() == 0) {
            loadMore();
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
            mIScroll.scrollTo_((int) dy);
        }
    }

    protected void realCancel() {
        if (state == PULL) {
            //回弹
            mScrollAnimation = ScrollAnimation.DisRefreshAble_BackAnimation;
            mIScroll.smoothScrollTo_(0);
            log("回弹！");
        }
        if (state == REFRESH_ABLE) {
            //刷新
            mScrollAnimation = ScrollAnimation.RefreshAble_BackAnimation;
            mIScroll.smoothScrollTo_(getRefreshAbleHeight());
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return state == REST && super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    protected boolean canChildScrollUp() {
        return ScrollingUtil.canChildScrollUp(contentView);
    }

    @Override
    protected void moveSpinner(float mTotalUnconsumed) {
        //todo
        realMove(mTotalUnconsumed);
    }

    @Override
    protected void finishSpinner() {
        realCancel();
    }

    void loadMore() {
        state = LOADMORE_ING;
        if (!isDelegate && mIFooterView != null) {
            mIFooterView.onStart(this, footerView.getHeight());
        }
    }

    void notityLoadMoreListener() {
        state = LOADMORE_ING;
        log("LOADMORE_ING！");
        delayAutoComplete();
        mLoadMoreListener.loadMore(this);
    }


    void notityRefresh() {
        state = REFRESHING;
        log("释放回弹 将要刷新  refreshing!");
        //等待回调  刷新完成
        if (mPullListener != null)
            mPullListener.refresh(this);
        if (mIHeaderView != null)
            mIHeaderView.onRefreshing(getRefreshAbleHeight(), false);

    }

    Runnable runableAutoComplete = new Runnable() {
        @Override
        public void run() {
            if (ZRefreshLayout.this != null && ZRefreshLayout.this.isRefreshOrLoadMore()) {
                log("延迟结束刷新/加载!");
                ZRefreshLayout.this.refresh2LoadMoreComplete();
            }
        }
    };

    public void setDelayAutoCompleteTime(int delayAutoComplete) {
        this.delay_millis_auto_complete = delayAutoComplete;
    }

    public int getDelayAutoCompleteTime() {
        return delay_millis_auto_complete;
    }

    private void delayAutoComplete() {
        if (delay_millis_auto_complete != -1) {
            log("add延迟完成!");
            mHandler.postDelayed(runableAutoComplete, delay_millis_auto_complete);

        }
    }

    private void removeDelayAutoComplete() {
        if (delay_millis_auto_complete != -1) {
            log("remove延迟完成!");
            mHandler.removeCallbacks(runableAutoComplete);
        }
    }

    int heightToRefresh;

    //因为头部设置更有效 所以就暴露了  通过AUtils类暴露接口
    void setHeaderHeightToRefresh(int heightToRefresh) {
        this.heightToRefresh = heightToRefresh;
    }

    int getRefreshAbleHeight() {
        return heightToRefresh != 0 ? heightToRefresh : headerView.getHeight();
    }

    public void autoRefresh(boolean haveAnimate) {
        if (state == REST) {
            //等待回调  刷新完成
            state = AUTO_PULL;
            log("AUTO_PULL！");
            delayAutoComplete();

            if (!haveAnimate) {
                //无动画的 就不做位置映射 而知道到刷新位置
                mIScroll.scrollTo_(getRefreshAbleHeight());

            } else {
                mScrollAnimation = ScrollAnimation.AutoRefresh_Animation;
                mIScroll.smoothScrollTo_(getRefreshAbleHeight());
            }
            //自动刷新是  下拉过程就开始有动画了
            notityRefresh();
        }
    }

    public void refreshComplete() {
        if (!isRefresh()) {
            log("非刷新状态下 refreshComplete");
            return;
        }
        removeDelayAutoComplete();
        mScrollAnimation = ScrollAnimation.Complete_BackAnimation;
        mIScroll.smoothScrollTo_(0);
    }

    public void loadMoreComplete() {
        if (!isLoadMore()) {
            log("非加载状态下 loadMoreComplete");
            return;
        }
        removeDelayAutoComplete();
        if (!isDelegate && mIFooterView != null)
            mIFooterView.onComplete(this);
    }

    void notifyLoadMoreCompleteListener() {
        state = REST;
        if (mLoadMoreStateRestListener != null)
            mLoadMoreStateRestListener.loadMoreStateRestComplete(this);
        haveDownEvent = false;
    }

    public LoadMoreListener getLoadMoreListener() {
        return mLoadMoreListener;
    }

    boolean isInterceptInnerLoadMore;
    boolean isDelegate;
    LoadMoreOtherListener outterLoadMoreListener;

    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        setLoadMoreListener(false, mLoadMoreListener);
    }

    /**
     * @param mLoadMoreListener
     * @param mLoadMoreStateRestListener Listening to the LoadMore State Rest Complete
     */
    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener, LoadMoreStateRestListener mLoadMoreStateRestListener) {
        setLoadMoreListener(false, mLoadMoreListener);
        this.mLoadMoreStateRestListener = mLoadMoreStateRestListener;
    }

    /**
     * @param isDelegate        委托加载更多,在外边处理
     *                          外部监听请调用此方法{@link AUtils#notityLoadMoreListener(ZRefreshLayout)}
     *                          false，会走内部的LoadMoreController
     *                          true：的话不委托，既会跳过 LoadMoreController。这样更快
     * @param mLoadMoreListener
     */
    public void setLoadMoreListener(boolean isDelegate, LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
        this.isDelegate = isDelegate;
        if (!isDelegate) {
            outterLoadMoreListener = LoadMoreController.addLoadMoreListener(contentView, this);
            isInterceptInnerLoadMore = outterLoadMoreListener == null ? false : true;
        } else
            isInterceptInnerLoadMore = true;
    }

    public boolean isCanLoadMore() {
        return isCanLoadMore && mLoadMoreListener != null;
    }

    public boolean isLoadMore() {
        return state == LOADMORE_ING;
    }

    public boolean isRefreshOrLoadMore() {
        return isLoadMore() || isRefresh();
    }

    public void refresh2LoadMoreComplete() {
        if (isRefresh())
            refreshComplete();
        if (isLoadMore())
            loadMoreComplete();
    }

    public boolean isRefresh() {
        return state == REFRESHING;
    }


    //必须设置加载更多监听才能 加载更多
    public void setCanLoadMore(boolean canLoadMore) {
        isCanLoadMore = canLoadMore;
        if (outterLoadMoreListener == null)
            return;
        if (canLoadMore) {
            if (!outterLoadMoreListener.haveListener())
                outterLoadMoreListener.addListener(contentView, this);
        } else
            outterLoadMoreListener.removeListener(contentView);
    }

    public boolean isCanRefresh() {
        return isCanRefresh;
    }

    public void setCanRefresh(boolean canRefresh) {
        isCanRefresh = canRefresh;
    }

    public PullListener getPullListener() {
        return mPullListener;
    }

    public void setPullListener(PullListener mPullListener) {
        this.mPullListener = mPullListener;
    }

    /**
     * @param mPullListener
     * @param mPullStateRestListener Listening to the refresh State Rest Complete
     */
    public void setPullListener(PullListener mPullListener, PullStateRestListener mPullStateRestListener) {
        this.mPullListener = mPullListener;
        this.mPullStateRestListener = mPullStateRestListener;
    }

    public PullStateRestListener getPullStateRestListener() {
        return mPullStateRestListener;
    }

    public LoadMoreStateRestListener getLoadMoreStateRestListener() {
        return mLoadMoreStateRestListener;
    }

    public IHeaderView getIHeaderView() {
        return mIHeaderView;
    }

    public void setIHeaderView(@NonNull IHeaderView mIHeaderView) {
        this.mIHeaderView = mIHeaderView;
        removeView(headerView);
        heightToRefresh = 0;//还原刷新高度
        headerView = mIHeaderView.initView(this);
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

    public void setHeadPin(@NonNull HeadPin mHeadPin) {
        if (state == REST) generateHeadPin(mHeadPin);
    }
    public HeadPin getHeadPin() {
        return mHeadPin;
    }

    public IResistance getIResistance() {
        return mIResistance;
    }

    public void setIResistance(IResistance mIResistance) {
        this.mIResistance = mIResistance;
    }

    public RefreshAbleListener getRefreshAbleListener() {
        return mRefreshAbleListener;
    }

    public void setRefreshAbleListener(RefreshAbleListener mRefreshAbleListener) {
        this.mRefreshAbleListener = mRefreshAbleListener;
    }

    public List<IScroll> getIScrollList() {
        if (mIScrollList == null) {
            mIScrollList = new ArrayList<>();
        }
        return mIScrollList;
    }

    //-------------------------内部接口----------------------------------------------------
    public interface PullListener {
        void refresh(ZRefreshLayout zRefreshLayout);
    }

    public interface PullStateRestListener {
        void refreshStateRestComplete(ZRefreshLayout zRefreshLayout);
    }


    public interface RefreshAbleListener {
        void refreshAble(boolean refreshAble);
    }

    public interface LoadMoreListener {
        void loadMore(ZRefreshLayout zRefreshLayout);
    }

    public interface LoadMoreStateRestListener {
        void loadMoreStateRestComplete(ZRefreshLayout zRefreshLayout);
    }
}
