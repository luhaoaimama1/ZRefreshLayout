package zone.com.zrefreshlayout.header;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.IScroll;
import zone.com.zrefreshlayout.ScrollAnimation;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.LogUtils;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayout.v4.MeterialCircle;

/**
 * Created by fuzhipeng on 2017/1/12.
 */

public class MeterialHead implements IHeaderView {
    private  int[] colors;
    private MeterialCircle mMeterialCircle;
    private ZRefreshLayout zRefreshLayout;

    public MeterialHead(int[] colors) {
        this.colors = colors;
    }
    public MeterialHead() {
    }

    @Override
    public IHeaderView clone_() {
        return new MeterialHead(colors);
    }

    @Override
    public View initView(ZRefreshLayout zRefreshLayout) {
        this.zRefreshLayout = zRefreshLayout;
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        mMeterialCircle = new MeterialCircle(zRefreshLayout, (int) (screenPixs[1] * 0.065));
        if(colors!=null)
            mMeterialCircle.setColorSchemeColors(colors);
        AUtils.setHeaderHeightToRefresh(zRefreshLayout,(int) (screenPixs[1] * 0.065*2.0));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mMeterialCircle.getView().getLayoutParams();
        lp.bottomMargin= (int) (screenPixs[1] * 0.065/2);
        mMeterialCircle.getView().setLayoutParams(lp);
        return mMeterialCircle.getView();
    }

    @NonNull
    @Override
    public View getView() {
        return mMeterialCircle.getView();
    }


    @Override
    public void onPullingDown(float fraction, float headHeight) {
        mMeterialCircle.pullProgress(fraction);
    }

    @Override
    public void refreshAble(boolean refreshAble) {

    }

    @Override
    public void animateBack(ScrollAnimation scrollAnimation, float fraction, float headHeight, ZRefreshLayout.HeadPin isPinContent) {
    }

    @Override
    public boolean interceptAnimateBack(ScrollAnimation scrollAnimation, final IScroll iScroll) {
        if (zRefreshLayout.getHeadPin()== ZRefreshLayout.HeadPin.PIN && scrollAnimation == ScrollAnimation.Complete_BackAnimation) {
            mMeterialCircle.startScaleDownAnimation(new MeterialCircle.ScaleDownCallback() {
                @Override
                public void over() {
                    log("over()");
                    AUtils.notityRefreshCompeleStateToRest(iScroll);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onRefreshing(float headHeight, boolean isAutoRefresh) {
        if (isAutoRefresh) {
            log("startScaleUpAnimation");
//            mMeterialCircle.startScaleUpAnimation();
        }
        log("mProgress.start()");
        mMeterialCircle.start();
        log("onRefreshing");
    }

    @Override
    public void onComplete() {
        mMeterialCircle.reset();
        mMeterialCircle.getView().setTranslationY(0);//众神归位
        log("mProgress.reset()");
        log("onComplete");
    }

    private void log(String log) {
        LogUtils.log("MeterialHead Log:"+log);
    }
}
