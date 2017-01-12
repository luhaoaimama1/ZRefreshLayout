package zone.com.zrefreshlayout.header;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.v4.CircleImageView;
import zone.com.zrefreshlayout.v4.MaterialProgressDrawable;

/**
 * Created by fuzhipeng on 2017/1/12.
 */

public class MeterialHead  implements IHeaderView {
    // Default background for the progress spinner
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private CircleImageView mCircleView;
    private MaterialProgressDrawable mProgress;
    private boolean refreshAble;

    @Override
    public IHeaderView clone_() {
        MeterialHead clone = new MeterialHead();
        return clone;
    }

    @Override
    public View getView(Context context) {
        View rootView = View.inflate(context, R.layout.header_meterial, null);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        LinearLayout ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        mCircleView = new CircleImageView(context, CIRCLE_BG_LIGHT);
        mProgress = new MaterialProgressDrawable(context, ll_main);
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mCircleView.setImageDrawable(mProgress);
//        mCircleView.setVisibility(View.GONE);
        ll_main.addView(mCircleView);
        mMediumAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        return rootView;
    }
    private static final int MAX_ALPHA = 255;
    private Animation mScaleAnimation;
    private Animation mScaleDownAnimation;
    private static final int SCALE_DOWN_DURATION = 150;
    private int mMediumAnimationDuration;

    private void startScaleUpAnimation(Animation.AnimationListener listener) {
        mCircleView.setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // Pre API 11, alpha is used in place of scale up to show the
            // progress circle appearing.
            // Don't adjust the alpha during appearance otherwise.
            mProgress.setAlpha(MAX_ALPHA);
        }
        mScaleAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {

                setAnimationProgress(interpolatedTime);
            }
        };
        mScaleAnimation.setDuration(mMediumAnimationDuration);
        if (listener != null) {
            mCircleView.setAnimationListener(listener);
        }
        mCircleView.clearAnimation();
        mCircleView.startAnimation(mScaleAnimation);
    }
    /**
     * Pre API 11, alpha is used to make the progress circle appear instead of scale.
     */
    private boolean isAlphaUsedForScale() {
        return android.os.Build.VERSION.SDK_INT < 11;
    }
    /**
     * Pre API 11, this does an alpha animation.
     * @param progress
     */
    void setAnimationProgress(float progress) {
        if (isAlphaUsedForScale()) {
            setColorViewAlpha((int) (progress * MAX_ALPHA));
        } else {
            ViewCompat.setScaleX(mCircleView, progress);
            ViewCompat.setScaleY(mCircleView, progress);
        }
    }
    /**
     * One of DEFAULT, or LARGE.
     */
    public void setSize(int size) {
        if (size != MaterialProgressDrawable.LARGE && size != MaterialProgressDrawable.DEFAULT) {
            return;
        }
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        if (size == android.support.v4.widget.MaterialProgressDrawable.LARGE) {
            mCircleDiameter = (int) (CIRCLE_DIAMETER_LARGE * metrics.density);
        } else {
            mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
        }
        // force the bounds of the progress circle inside the circle view to
        // update by setting it to null before updating its size and then
        // re-setting it
        mCircleView.setImageDrawable(null);
        mProgress.updateSizes(size);
        mCircleView.setImageDrawable(mProgress);
    }
    private void setColorViewAlpha(int targetAlpha) {
        mCircleView.getBackground().setAlpha(targetAlpha);
        mProgress.setAlpha(targetAlpha);
    }

    void startScaleDownAnimation(Animation.AnimationListener listener) {
        mScaleDownAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(1 - interpolatedTime);
            }
        };
        mScaleDownAnimation.setDuration(SCALE_DOWN_DURATION);
        mCircleView.setAnimationListener(listener);
        mCircleView.clearAnimation();
        mCircleView.startAnimation(mScaleDownAnimation);
    }
    @Override
    public void onPullingDown(float fraction, float headHeight, ZRefreshLayout.RefreshAbleListener mRefreshAbleListener) {
        if (fraction >= 1f) {
            if (!refreshAble) {
                refreshAble = true;
                refreshAble(refreshAble, mRefreshAbleListener);
            }
        } else {
            if (refreshAble) {
                refreshAble = false;
                refreshAble(refreshAble, mRefreshAbleListener);
            }
        }
    }

    private void refreshAble(boolean refreshAble, ZRefreshLayout.RefreshAbleListener mRefreshAbleListener) {
        if (refreshAble) {
            if (mRefreshAbleListener != null)
                mRefreshAbleListener.refreshAble();
            startScaleDownAnimation(null);
        } else {
            if (mRefreshAbleListener != null)
                mRefreshAbleListener.refreshDisAble();
            startScaleUpAnimation(null);
        }
    }

    @Override
    public void onPullReleasing(float fraction, float headHeight) {

    }

    @Override
    public void onRefreshing(float headHeight) {

    }

    @Override
    public void onComplete() {

    }
}
