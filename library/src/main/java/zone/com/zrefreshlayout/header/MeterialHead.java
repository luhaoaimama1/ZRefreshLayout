package zone.com.zrefreshlayout.header;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.AnimateBack;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayout.v4.MeterialCircle;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

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
        MeterialHead clone = new MeterialHead(colors);
        return clone;
    }

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        this.zRefreshLayout = zRefreshLayout;
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        mMeterialCircle = new MeterialCircle(zRefreshLayout, (int) (screenPixs[1] * 0.065));
        if(colors!=null)
            mMeterialCircle.setColorSchemeColors(colors);
        AUtils.setHeaderHeightToRefresh(zRefreshLayout,(int) (screenPixs[1] * 0.065*2.0));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mMeterialCircle.getView().getLayoutParams();
        lp.bottomMargin=66;
        mMeterialCircle.getView().setLayoutParams(lp);
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
    public void animateBack(AnimateBack animateBack, float fraction, float headHeight, boolean isPinContent) {
    }

    @Override
    public boolean interceptAnimateBack(AnimateBack animateBack, final ZRefreshLayout.IScroll iScroll) {
        if (zRefreshLayout.isPinContent() && animateBack == AnimateBack.Complete_Back) {
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
}
