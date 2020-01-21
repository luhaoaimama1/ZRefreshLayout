package zone.com.zrefreshlayout;


import android.view.animation.LinearInterpolator;

import android.animation.Animator;
import android.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.utils.SimpleAnimatorListener;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

public abstract class IScroll {

    protected ZRefreshLayout mZRefreshLayout;
    //当isInterceptRefreshCompeleStateToRest=true的时候代表刷新完成通知由刷新头部动画完成后头部回调
    protected boolean isInterceptRefreshCompeleStateToRest =false;

    public IScroll(ZRefreshLayout mZRefreshLayout) {
        this.mZRefreshLayout = mZRefreshLayout;
    }

    private static final int DEFAULT_DURATION = 250;
    private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofInt(0, 100)
            .setDuration(DEFAULT_DURATION)
            .setInterpolator(new LinearInterpolator())
            .addListener(new SimpleAnimatorListener() {

                @Override
                public void onAnimationEnd(Animator animation) {
//                        到可刷新位置:通知刷新
//                        由于AutoRefresh_Animation是下拉就有动画所以通知刷新不在这里
//                        仅仅RefreshAble_BackAnimation 通知刷新

//                        归0 :状态恢复操作
//                        DisRefreshAble_BackAnimation,Complete_BackAnimation
                    log("onAnimationEnd : Scrolling animation:" + mZRefreshLayout.mScrollAnimation);
                    if (mZRefreshLayout.mScrollAnimation == ScrollAnimation.RefreshAble_BackAnimation) {
                        //超过刷新刷新位置 回弹到可刷新位置的动画结束后,后执行刷新动画
                        mZRefreshLayout.notityRefresh();
                    }

                    if (!isInterceptRefreshCompeleStateToRest &&
                            mZRefreshLayout.mScrollAnimation == ScrollAnimation.Complete_BackAnimation ||
                            mZRefreshLayout.mScrollAnimation == ScrollAnimation.DisRefreshAble_BackAnimation) {
                        refreshCompeleStateToRest();
                    }

                }
            })
            .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer offset = (Integer) animation.getAnimatedValue();
                    //主要 监听滚动动画的位置
                    if (mZRefreshLayout.mIHeaderView != null)
                        mZRefreshLayout.mIHeaderView.animateBack(mZRefreshLayout.mScrollAnimation,
                                1F * Math.abs(getScrollY()) / mZRefreshLayout.getRefreshAbleHeight(),
                                mZRefreshLayout. getRefreshAbleHeight(), mZRefreshLayout.getHeadPin());
                    scrollToInner(offset, false);
                }

            });

    private void notifyScrollList(Integer offset, boolean isTriggerHeaderOnPullingDown) {

    }

    /**
     * 给AUtils调用的需要检测
     */
    void refreshCompeleStateToRest_AUtils() {
        if (mZRefreshLayout.mScrollAnimation == ScrollAnimation.Complete_BackAnimation
                || mZRefreshLayout.mScrollAnimation == ScrollAnimation.DisRefreshAble_BackAnimation)
            refreshCompeleStateToRest();
        else
            throw new IllegalStateException("state rest must be Complete_BackAnimation or DisRefreshAble_BackAnimation!");
    }

    private void refreshCompeleStateToRest() {
        log("State began to reset！");
//            if (state == REFRESHING || state == PULL) {
        log("Roll back to zero!");
        mZRefreshLayout.state = ZRefreshLayout.COMPLETE;
        if (mZRefreshLayout.mIHeaderView != null)
            mZRefreshLayout.mIHeaderView.onComplete();
        mZRefreshLayout.state = ZRefreshLayout.REST;
//                if (mPullListener != null && state == REFRESHING) {
        if (mZRefreshLayout.mPullStateRestListener != null && mZRefreshLayout.mScrollAnimation == ScrollAnimation.Complete_BackAnimation) {
            log("mPullListener.refreshStateRestComplete");
            mZRefreshLayout.mPullStateRestListener.refreshStateRestComplete(mZRefreshLayout);
        }
        mZRefreshLayout.mScrollAnimation = ScrollAnimation.None;
        mZRefreshLayout.haveDownEvent = false;
        log("State is reset!");
    }

    protected void smoothScrollTo_(int fy) {
        //拦截滚动动画
        if (mZRefreshLayout.mIHeaderView.interceptAnimateBack(mZRefreshLayout.mScrollAnimation, IScroll.this)){
            isInterceptRefreshCompeleStateToRest =true;
            return;
        }
        isInterceptRefreshCompeleStateToRest =false;
        valueAnimator.setIntValues(getScrollY(), fy);
        valueAnimator.start();
    }

    void smoothScrollTo_NotIntercept(int fy) {
        isInterceptRefreshCompeleStateToRest =false;
        valueAnimator.setIntValues(getScrollY(), fy);
        valueAnimator.start();
    }

    /**
     * @param fy
     */
    protected void scrollTo_(int fy) {
        int fy1 = fy > 0 ? fy : 0;
        scrollToInner(fy1,true);
    }

    /**
     * @param fy
     * @param isTriggerHeaderOnPullingDown 是否不做位置映射
     */
    protected abstract void scrollTo(int fy, boolean isTriggerHeaderOnPullingDown);

    int scrollFy = 0;

    private   void scrollToInner(int fy, boolean isTriggerHeaderOnPullingDown){
        scrollFy=fy;
        scrollTo(fy, isTriggerHeaderOnPullingDown);
        if (mZRefreshLayout.mIScrollList != null) {
            for (IScroll iScroll : mZRefreshLayout.mIScrollList) {
                iScroll.scrollTo(fy, isTriggerHeaderOnPullingDown);
            }
        }
    };

    protected  int getScrollY() {
        return scrollFy;
    }
}