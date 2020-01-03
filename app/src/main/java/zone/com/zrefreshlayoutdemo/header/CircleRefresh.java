package zone.com.zrefreshlayoutdemo.header;

import android.app.Activity;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.IScroll;
import zone.com.zrefreshlayout.ScrollAnimation;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayoutdemo.header.view.AnimationView;
import static zone.com.zrefreshlayout.utils.LogUtils.log;
/**
 * Created by fuzhipeng on 2017/1/13.
 */

public class CircleRefresh implements IHeaderView {

    private View rootView;
    private AnimationView mHeaderWaveCircle;
    private int mHeaderCircleSmaller = 6;
    private IScroll iScroll;
    private LinearLayout ll_main;
    private int[] screenPixs;


    @Override
    public View initView(ZRefreshLayout zRefreshLayout) {
        rootView = View.inflate(zRefreshLayout.getContext(), R.layout.header_meterial, null);
//        rootView.setBackgroundColor(Color.RED);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.2)));
        AUtils.setHeaderHeightToRefresh(zRefreshLayout,(int) (screenPixs[1] * 0.2*0.8));
        addView();
        return rootView;
    }

    @NonNull
    @Override
    public View getView() {
        return rootView;
    }

    private void addView() {
        ll_main.removeAllViews();
        mHeaderWaveCircle = new AnimationView(rootView.getContext());
        mHeaderWaveCircle.setBackgroundColor(Color.parseColor("#ffdb6e"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenPixs[1] * 0.2));
        mHeaderWaveCircle.setLayoutParams(params);

        ll_main.addView(mHeaderWaveCircle);
        mHeaderWaveCircle.setAniBackColor(Color.parseColor("#ffdb6e"));
        mHeaderWaveCircle.setAniForeColor(Color.WHITE);
        mHeaderWaveCircle.setRadius(mHeaderCircleSmaller);
        mHeaderWaveCircle.setOnViewAniDone(new AnimationView.OnViewAniDone() {
            @Override
            public void viewAniDone() {
//                Log.i(TAG, "should invoke");
                log("viewAniDone");
                //刷新动画 延迟完毕
                AUtils.smoothScrollTo_NotIntercept(iScroll,0);
            }
        });
    }

    @Override
    public void onPullingDown(float fraction, float headHeight) {
    }

    @Override
    public void refreshAble(boolean refreshAble) {
        if (refreshAble) {
            mHeaderWaveCircle.releaseDrag();
        }
    }

    private ScrollAnimation mScrollAnimation = ScrollAnimation.None;

    @Override
    public void animateBack(ScrollAnimation scrollAnimation, float fraction, float headHeight, ZRefreshLayout.HeadPin isPinContent) {
        mScrollAnimation = scrollAnimation;
    }


    //这里主要是对延迟刷新的处理
    @Override
    public boolean interceptAnimateBack(ScrollAnimation scrollAnimation, IScroll iScroll) {
        this.iScroll = iScroll;
        boolean result = false;
        if (mScrollAnimation != scrollAnimation && scrollAnimation == ScrollAnimation.Complete_BackAnimation) {
            mHeaderWaveCircle.setRefreshing(false);//刷新动画
            result = true;
        }
        mScrollAnimation = scrollAnimation;
        //返回结果就是是否拦截
        return result;
    }

    @Override
    public void onRefreshing(float headHeight, boolean isAutoRefresh) {

    }

    @Override
    public void onComplete() {
        addView();
    }

    @Override
    public IHeaderView clone_() {
        return new CircleRefresh();
    }
}
