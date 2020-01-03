package zone.com.zrefreshlayout.footer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.IFooterView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.ScreenUtils;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class LoadFooter implements IFooterView {
    private View rootView;
    private ImageView loadingView;
    private ZRefreshLayout zRefreshLayout;

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        this.zRefreshLayout=zRefreshLayout;
        rootView= View.inflate(zRefreshLayout.getContext(), R.layout.footer, null);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.1)));

        ViewGroup.LayoutParams loadingViewLp = loadingView.getLayoutParams();
        loadingViewLp.width = (int) (screenPixs[1] * 0.05);
        loadingViewLp.height =(int) (screenPixs[1] * 0.05);
        loadingView.setLayoutParams(loadingViewLp);
        return rootView;
    }

    @Override
    public void onStart(ZRefreshLayout zRefreshLayout, float footerHeight) {
        loadingView.setVisibility(View.VISIBLE);
        valueAnimator.start();
        AUtils.notityLoadMoreListener(zRefreshLayout);
    }

    @Override
    public void onComplete(ZRefreshLayout zRefreshLayout) {
        if(valueAnimator.isRunning())
            valueAnimator.end();
        loadingView.setVisibility(View.INVISIBLE);
        AUtils.notifyLoadMoreCompleteListener(zRefreshLayout);
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
                    loadingView.setRotation(((Integer) animation.getAnimatedValue()/30)*30F);
                }
            });
    @Override
    public IFooterView clone_() {
        LoadFooter clone =new LoadFooter();
        return clone;
    }
}
