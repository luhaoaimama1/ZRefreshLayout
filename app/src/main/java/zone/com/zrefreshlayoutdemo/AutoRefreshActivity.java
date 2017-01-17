package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.header.SinaRefreshHeader;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class AutoRefreshActivity extends AppCompatActivity {
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unique_feature);
        ButterKnife.bind(this);
        FrameLayout.LayoutParams lp= (FrameLayout.LayoutParams) tv.getLayoutParams();
        lp.gravity=Gravity.BOTTOM;
        tv.setLayoutParams(lp);
        tv.setText("2秒后,有动画刷新。\n 点我,2秒后 无动画自动刷新");
//        refresh.setIHeaderView(new SinaRefreshHeader());
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageResource(R.drawable.aaaaaaaaaaaab);
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void complete(ZRefreshLayout zRefreshLayout) {

            }
        });
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.autoRefresh(true);
            }
        }, 2000);
    }

    @OnClick(R.id.tv)
    public void onClick() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.autoRefresh(false);
            }
        }, 2000);
    }
}
