package zone.com.zrefreshlayout.v4;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.utils.SimpleAnimatorListener;

/**
 * Created by fuzhipeng on 2017/1/13.
 */

public class MeterialCircle {
    // Default background for the progress spinner
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private CircleImageView mCircleView;
    private MaterialProgressDrawable mProgress;
    private static final int SCALE_DOWN_DURATION = 150;
    private static final int MAX_ALPHA = 255;

    int[] colors_red_green_yellow=new int[]{
            Color.parseColor("#ffF44336"),
            Color.parseColor("#ff4CAF50"),
            Color.parseColor("#ffFFEB3B")
    };
    private ScaleDownCallback mScaleDownCallback;

    public MeterialCircle(ViewGroup vp, int width2height) {
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        mCircleView = new CircleImageView(vp.getContext(), CIRCLE_BG_LIGHT);
        mCircleView.setLayoutParams(new LinearLayout.LayoutParams(width2height, width2height));
        mProgress = new MaterialProgressDrawable(vp.getContext(), vp);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mProgress.setColorSchemeColors(colors_red_green_yellow);
        mCircleView.setImageDrawable(mProgress);
        vp.addView(mCircleView);
//        mCircleView.setVisibility(View.GONE);
    }

    public void setRadius(int width2height) {
        mCircleView.setLayoutParams(new LinearLayout.LayoutParams(width2height, width2height));
    }
    public void setColorSchemeColors(int... colors) {
        mProgress.setColorSchemeColors(colors);
    }

    private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofFloat(0, 1F)
            .setDuration(SCALE_DOWN_DURATION)
            .addListener(new SimpleAnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(mScaleDownCallback!=null)
                        mScaleDownCallback.over();
                }

            })
            .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setAnimationProgress((Float) animation.getAnimatedValue());
//                    log("onAnimationUpdate:" + (Float) animation.getAnimatedValue());
                }
            });


    /**
     * Pre API 11, this does an alpha animation.
     *
     * @param progress
     */
    void setAnimationProgress(float progress) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            setColorViewAlpha((int) (progress * MAX_ALPHA));
        } else {
            ViewCompat.setScaleX(mCircleView, progress);
            ViewCompat.setScaleY(mCircleView, progress);
        }
    }

    private void setColorViewAlpha(int targetAlpha) {
        mCircleView.getBackground().setAlpha(targetAlpha);
        mProgress.setAlpha(targetAlpha);
    }

    public void startScaleDownAnimation() {
        valueAnimator.setFloatValues(1F, 0F).start();
    }

    public void startScaleDownAnimation(ScaleDownCallback mScaleDownCallback) {
        this.mScaleDownCallback=mScaleDownCallback;
        startScaleDownAnimation();
    }

    public void setVisibility(int visibility ) {
        mCircleView.setVisibility(visibility);
    }

    public void startScaleUpAnimation() {
        mCircleView.setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // Pre API 11, alpha is used in place of scale up to show the
            // progress circle appearing.
            // Don't adjust the alpha during appearance otherwise.
            mProgress.setAlpha(MAX_ALPHA);
        }
        valueAnimator.setFloatValues(0, 1).start();
    }

    public void pullProgress(float fraction) {
        float percent = Math.min(1f, fraction);
        mProgress.setAlpha((int) (255 * percent));
        mProgress.showArrow(true);

        float strokeStart = ((percent) * .8f);
        mProgress.setStartEndTrim(0f, Math.min(0.8f, strokeStart));
        mProgress.setArrowScale(Math.min(1f, percent));

        // magic
        float rotation = (-0.25f + .4f * percent + percent * 2) * .5f;
        mProgress.setProgressRotation(rotation);
    }

    public void pullProgressNotArrow(float fraction) {
        float percent = Math.min(1f, fraction);
        mProgress.setAlpha((int) (255 * percent));
        mProgress.showArrow(false);

        float strokeStart = ((percent) * .8f);
        mProgress.setStartEndTrim(0f, Math.min(0.8f, strokeStart));
        mProgress.setArrowScale(Math.min(1f, percent));

        // magic
        float rotation = (-0.25f + .4f * percent + percent * 2) * .5f;
        mProgress.setProgressRotation(rotation);
    }

    public void start() {
        pullProgress(1);
        mProgress.showArrow(false);
        mProgress.setAlpha(255);
        mProgress.start();
    }

    public void stop() {
        setAnimationProgress(1);
        mProgress.stop();
    }
    public void stop2() {
        mProgress.stop();
    }

    public interface  ScaleDownCallback{
        void over();
    }

    public CircleImageView getCircleView() {
        return mCircleView;
    }

}
