package zone.com.zrefreshlayoutdemo.header;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ValueAnimator;

import and.utils.image.BitmapComposer;
import and.utils.image.WaveHelper;
import and.utils.image.compress2sample.SampleUtils;
import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.AnimateBack;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import static zone.com.zrefreshlayout.utils.LogUtils.log;
/**
 * Created by fuzhipeng on 2017/1/13.
 */

public class WaveHead implements IHeaderView {

    private View rootView;
    private ImageView imageView;
    private int resourceId;
    private static final int ANIMATION_DURATION = 1332 * 2;
    private WaveUtils mWaveUtils;

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        rootView = View.inflate(zRefreshLayout.getContext(), R.layout.header_meterial, null);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        LinearLayout ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.2)));
        imageView = new ImageView(zRefreshLayout.getContext());
        int imageViewHeight2width = (int) (screenPixs[1] * 0.2);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(imageViewHeight2width, imageViewHeight2width));
        ll_main.addView(imageView);
        mWaveUtils = new WaveUtils();
        mWaveUtils.wave(zRefreshLayout.getContext(), imageViewHeight2width);
        return rootView;
    }

    @Override
    public void onPullingDown(float fraction, float headHeight) {
        imageView.setRotationX(90 - fraction * 90);
    }

    @Override
    public void refreshAble(boolean refreshAble) {

    }

    @Override
    public void animateBack(AnimateBack animateBack, float fraction, float headHeight, boolean mIScroll) {
        if (animateBack == AnimateBack.RefreshAble_Back)
            imageView.setRotationX(90 - fraction * 90);
    }

    @Override
    public boolean interceptAnimateBack(AnimateBack animateBack, ZRefreshLayout.IScroll iScroll) {
        return false;
    }

    @Override
    public void onRefreshing(float headHeight, boolean isAutoRefresh) {
        mWaveUtils.valueAnimator.start();
        mWaveUtils.mWaveHelper.start();
    }

    @Override
    public void onComplete() {
        imageView.setRotationX(90);
        mWaveUtils.valueAnimator.end();
        mWaveUtils.mWaveHelper.cancel();
        mWaveUtils.mWaveHelper.setLevelProgress(0F);
    }

    @Override
    public IHeaderView clone_() {
        WaveHead clone = new WaveHead();
        clone.resourceId = resourceId;
        return clone;
    }


    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


    class WaveUtils {

        private WaveHelper mWaveHelper;
        private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofFloat(0, 1F)
                .setDuration(ANIMATION_DURATION)
                .setRepeatCount(ValueAnimator.INFINITE)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (mWaveHelper != null)
                            mWaveHelper.setLevelProgress((Float) animation.getAnimatedValue());
                        log("onAnimationUpdate:" + (Float) animation.getAnimatedValue());
                    }
                });

        private void wave(final Context context, final int imageViewHeight2width) {
            if (resourceId == -1)
                throw new IllegalStateException("resourceId not be null!");
            final Bitmap bt = SampleUtils.load(context, resourceId)
                    .override(imageViewHeight2width, imageViewHeight2width)
                    .bitmap();

            final ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);

            final BitmapComposer bitmapComposer = BitmapComposer.newComposition(bt.getWidth(), bt.getHeight(), Bitmap.Config.ARGB_4444);
//        Matrix first = new Matrix();
//        first.postTranslate(0, -20);
            mWaveHelper = new WaveHelper(bt.getWidth(), bt.getHeight(), new WaveHelper.RefreshCallback() {
                @Override
                public void refresh(Bitmap wave) {
                    Bitmap render = bitmapComposer.clear()
                            .newLayer(
                                    BitmapComposer.Layer.bitmap(bt)
                                            .colorFilter(new ColorMatrixColorFilter(colorMatrix))
                            ).newLayer(
                                    BitmapComposer.Layer.bitmap(bt)
                                            .mask(wave, PorterDuff.Mode.DST_IN)
//                                                .matrix(first)
                            ).render();
                    imageView.setImageBitmap(render);
                }
            });
            mWaveHelper.cancel();
            mWaveHelper.setAmplitude(imageViewHeight2width / 16);
            mWaveHelper.setLength(imageViewHeight2width / 1.2F);
            mWaveHelper.setSpeed(imageViewHeight2width * 1.2F);
            mWaveHelper.setLevelProgress(0F);
        }


    }
}
