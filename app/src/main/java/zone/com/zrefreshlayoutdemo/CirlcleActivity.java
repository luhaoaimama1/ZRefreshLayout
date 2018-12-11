package zone.com.zrefreshlayoutdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayout.v4.MeterialCircle;

/**
 * [2017] by Zone
 */

public class CirlcleActivity extends AppCompatActivity {

    @BindView(R.id.root)
    RelativeLayout root;
    public MeterialCircle mMeterialCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_circle);
        ButterKnife.bind(this);
        root.post(new Runnable() {

            @Override
            public void run() {
                int[] screenPixs = ScreenUtils.getScreenPix(CirlcleActivity.this);
                mMeterialCircle=new MeterialCircle(root,(int) (screenPixs[1] * 0.2));
                root.addView(mMeterialCircle.getView());
            }
        });
    }

    private ValueAnimatorProxy valueAnimator = ValueAnimatorProxy.ofFloat(0, 1F)
            .setDuration(1200)
            .setInterpolator(new LinearInterpolator())
            .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    System.out.println("值?"+(Float) animation.getAnimatedValue());
                    mMeterialCircle.pullProgress((Float) animation.getAnimatedValue());
                }
            })
            .addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mMeterialCircle.start();
                }
            });
    @OnClick({R.id.bt_start, R.id.bt_stop, R.id.bt_reStart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if(valueAnimator.isRunning())
                    valueAnimator.cancel();
                mMeterialCircle.reset();
                valueAnimator.start();
                break;
            case R.id.bt_stop:
                mMeterialCircle.reset();
                break;
            case R.id.bt_reStart:
                mMeterialCircle.startScaleDownAnimation(new MeterialCircle.ScaleDownCallback() {
                    @Override
                    public void over() {
                        Log.e("CirlcleActivity","over");
                    }
                });
                break;
        }
    }
}
