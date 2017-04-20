package zone.com.zrefreshlayout.v4;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

/**
 * Created by fuzhipeng on 2017/1/13.
 */

public class MeterialCircle {
    // Default background for the progress spinner
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private CircleImageView mCircleView;
    private MaterialProgressDrawable mProgress;
    //    private static final int SCALE_DOWN_DURATION = 150;
    private static final int SCALE_DOWN_DURATION = 255;
    private static final int MAX_ALPHA = 255;

    int[] colors_red_green_yellow = new int[]{
            Color.parseColor("#ffF44336"),
            Color.parseColor("#ff4CAF50"),
            Color.parseColor("#ffFFEB3B")
    };
    public MeterialCircle(ViewGroup vp, int width2height) {
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        mCircleView = new CircleImageView(vp.getContext(), CIRCLE_BG_LIGHT);
        mCircleView.setLayoutParams(new FrameLayout.LayoutParams(width2height, width2height));
        mProgress = new MaterialProgressDrawable(vp.getContext(), vp);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mProgress.setColorSchemeColors(colors_red_green_yellow);
        mCircleView.setImageDrawable(mProgress);
//        vp.addView(mCircleView);
//        mCircleView.setVisibility(View.GONE);
    }

    public CircleImageView getView(){
        return mCircleView;
    }
    public void setRadius(int width2height) {
        mCircleView.setLayoutParams(new LinearLayout.LayoutParams(width2height, width2height));
    }

    public void setColorSchemeColors(int... colors) {
        mProgress.setColorSchemeColors(colors);
    }


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

    boolean isStart;
    public void start() {
        pullProgress(1);
        mProgress.showArrow(false);
        mProgress.setAlpha(MAX_ALPHA);
        isStart=true;
        mProgress.start();

    }

    public void reset() {
        circleStop();
        mCircleView.setVisibility(View.VISIBLE);
        setAnimationProgress(1);
    }

    private void circleStop() {
        if(isStart){
            mProgress.stop();
            isStart=false;
        }
    }

    public void startScaleDownAnimation(final ScaleDownCallback mScaleDownCallback) {
        Animation mScaleDownAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(1 - interpolatedTime);
                log("interpolatedTime"+(1 - interpolatedTime));
            }
        };
        mScaleDownAnimation.setDuration(SCALE_DOWN_DURATION);
        mCircleView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                log("onAnimationEnd------->");
                mCircleView.clearAnimation();
                circleStop();
                if (mScaleDownCallback != null)
                    mScaleDownCallback.over();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mCircleView.clearAnimation();
        mCircleView.startAnimation(mScaleDownAnimation);

    }

    public interface ScaleDownCallback {
        void over();
    }
}
