package zone.com.zrefreshlayoutdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayout.v4.MeterialCircle;

/**
 * [2017] by Zone
 */

public class CirlcleActivity extends AppCompatActivity {

    @Bind(R.id.root)
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
            }
        });
    }

    @OnClick({R.id.bt_start, R.id.bt_stop, R.id.bt_reStart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                mMeterialCircle.start();
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
