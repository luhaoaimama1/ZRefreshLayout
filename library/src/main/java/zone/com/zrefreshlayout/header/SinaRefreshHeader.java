package zone.com.zrefreshlayout.header;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.IScroll;
import zone.com.zrefreshlayout.ScrollAnimation;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.DensityUtils;
import zone.com.zrefreshlayout.utils.LogUtils;
import zone.com.zrefreshlayout.utils.ScreenUtils;

/**
 * Created by lcodecore on 2016/10/2.
 */

public class SinaRefreshHeader implements IHeaderView {

    private ImageView refreshArrow;
    private ImageView loadingView;
    private TextView refreshTextView;
    private View rootView;
    private LinearLayout ll_main;

    public void setArrowResource(@DrawableRes int resId) {
        refreshArrow.setImageResource(resId);
    }

    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    private String pullDownStr = "下拉刷新";
    private String releaseRefreshStr = "释放刷新";
    private String refreshingStr = "正在刷新";

    @Override
    public IHeaderView clone_() {
        SinaRefreshHeader clone = new SinaRefreshHeader();
        return clone;
    }

    @Override
    public View initView(ZRefreshLayout zRefreshLayout) {
        rootView = LayoutInflater.from(zRefreshLayout.getContext()).inflate(R.layout.view_sinaheader,zRefreshLayout,false);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        screenAdapter(zRefreshLayout,zRefreshLayout.getContext());
        return rootView;
    }

    @NonNull
    @Override
    public View getView() {
        return rootView;
    }

    private void screenAdapter(ZRefreshLayout zRefreshLayout, Context context) {
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) context);
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.1)));
        ViewGroup.LayoutParams refreshArrowLp = refreshArrow.getLayoutParams();
        refreshArrowLp.width = getAnInt(71, screenPixs, true);
        refreshArrowLp.height = getAnInt(71, screenPixs, false);
        refreshArrow.setLayoutParams(refreshArrowLp);
        ViewGroup.LayoutParams loadingViewLp = loadingView.getLayoutParams();
        loadingViewLp.width = getAnInt(71, screenPixs, true);
        loadingViewLp.height = getAnInt(71, screenPixs, false);
        loadingView.setLayoutParams(loadingViewLp);
        //注意TextSiz 要的是sp 需要px转过去
        refreshTextView.setTextSize(DensityUtils.px2sp(context, getAnInt(35, screenPixs, true)));
    }

    //我的参考图是1080*1920
    private int getAnInt(int dx, int[] screenPixs, boolean isWidth) {
        if (isWidth)
            return (int) (1F * dx / 1080 * screenPixs[0]);
        else
            return (int) (1F * dx / 1920 * screenPixs[1]);
    }

    @Override
    public void onPullingDown(float fraction, float headHeight) {
    }

    @Override
    public void refreshAble(boolean refreshAble) {
        if (refreshAble) {
            refreshArrow.animate().rotation(-180).start();
            refreshTextView.setText(releaseRefreshStr);
        } else {
            refreshTextView.setText(pullDownStr);
            refreshArrow.animate().rotation(0).start();
        }
    }

    @Override
    public void animateBack(ScrollAnimation scrollAnimation, float fraction, float headHeight,
                            ZRefreshLayout.HeadPin mIScroll) {
    }

    @Override
    public boolean interceptAnimateBack(ScrollAnimation scrollAnimation, IScroll iScroll) {
        return false;
    }

    @Override
    public void onRefreshing(float headHeight, boolean isAutoRefresh) {
        refreshTextView.setText(refreshingStr);
        refreshArrow.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        valueAnimator.start();
        log("onRefreshing");
    }

    private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofInt(0, 360)
            .setDuration(1200)
            .setInterpolator(new LinearInterpolator())
            .setRepeatCount(ValueAnimator.INFINITE)
            .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
//                   30 =360/12;
//                    ZRefreshLayout.log("animation.getAnimatedValue()："+animation.getAnimatedValue()+"——————进度："+((Integer) animation.getAnimatedValue()/30)*30F);
                    loadingView.setRotation(((Integer) animation.getAnimatedValue() / 30) * 30F);
                }
            });

    @Override
    public void onComplete() {
        refreshArrow.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        if (valueAnimator.isRunning())
            valueAnimator.end();
        log("onComplete");
    }

    private void log(String log) {
        LogUtils.log("SinaRefreshHeader Log:"+log);
    }

}
