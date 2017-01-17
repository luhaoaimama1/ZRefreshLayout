package zone.com.zrefreshlayout.header;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import zone.com.zrefreshlayout.AnimateBack;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayout.v4.MeterialCircle;
import static zone.com.zrefreshlayout.utils.LogUtils.log;
/**
 * Created by fuzhipeng on 2017/1/12.
 */

public class MeterialHead implements IHeaderView {
    private MeterialCircle mMeterialCircle;
    private View rootView;

    @Override
    public IHeaderView clone_() {
        MeterialHead clone = new MeterialHead();
        return clone;
    }

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
         rootView = View.inflate(zRefreshLayout.getContext(), R.layout.header_meterial, null);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        LinearLayout ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.1)));
        mMeterialCircle=new MeterialCircle(ll_main,(int) (screenPixs[1] * 0.08));
        return rootView;
    }


    @Override
    public void onPullingDown(float fraction, float headHeight) {
        if (fraction <= 1f) {
            mMeterialCircle.pullProgress(fraction);
        }
    }

    @Override
    public void refreshAble(boolean refreshAble) {

    }

    private AnimateBack mAnimateBack = AnimateBack.None;

    @Override
    public void animateBack(AnimateBack animateBack, float fraction, float headHeight, boolean isPinContent) {
//        log("animateBack:" + fraction);
        if (this.mAnimateBack != AnimateBack.Complete_Back && animateBack == AnimateBack.Complete_Back) {
            mMeterialCircle.startScaleDownAnimation();
            log("startScaleDownAnimation");
        }
        if(isPinContent&& animateBack == AnimateBack.Complete_Back){
            rootView.setTranslationY(headHeight);
            log("isPinContent setTranslationY 固定");
        }
        this.mAnimateBack = animateBack;
    }

    @Override
    public boolean interceptAnimateBack(AnimateBack animateBack, ZRefreshLayout.IScroll iScroll) {
        return false;
    }

    @Override
    public void onRefreshing(float headHeight, boolean isAutoRefresh) {
        if (isAutoRefresh) {
            log("startScaleUpAnimation");
            mMeterialCircle.startScaleUpAnimation();
        }
        log("mProgress.start()");
        mMeterialCircle.start();
        log("onRefreshing");
    }

    @Override
    public void onComplete() {
        rootView.setTranslationY(0);//众神归位
        mMeterialCircle.stop();
        log("mProgress.stop()");
        log("onComplete");
    }
}
