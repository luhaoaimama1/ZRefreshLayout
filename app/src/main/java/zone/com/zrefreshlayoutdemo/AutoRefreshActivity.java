package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class AutoRefreshActivity extends AppCompatActivity {
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unique_feature_copy);
        ButterKnife.bind(this);
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
            public void refreshAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });

    }

    @OnClick({R.id.tv, R.id.tvAnimate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.autoRefresh(false);
                    }
                }, 1000);
                break;
            case R.id.tvAnimate:
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.autoRefresh(true);
                    }
                }, 1000);
                break;
        }
    }
}
