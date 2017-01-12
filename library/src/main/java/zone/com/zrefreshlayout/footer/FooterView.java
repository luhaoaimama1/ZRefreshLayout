package zone.com.zrefreshlayout.footer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.IFooterView;
import zone.com.zrefreshlayout.R;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class FooterView implements IFooterView {
    private View rootView;
    private ImageView loadingView;

    @Override
    public View getView(Context context) {
        rootView= View.inflate(context, R.layout.footer, null);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        return rootView;
    }

    @Override
    public void onStart(float footerHeight) {
        loadingView.setVisibility(View.VISIBLE);
        valueAnimator.start();
    }

    @Override
    public void onComplete() {
        if(valueAnimator.isRunning())
            valueAnimator.end();
        loadingView.setVisibility(View.INVISIBLE);

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
        FooterView clone =new FooterView();
        return clone;
    }
}
