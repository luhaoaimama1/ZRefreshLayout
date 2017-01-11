package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        setContentView(R.layout.auto_refresh);
        ButterKnife.bind(this);
        refresh.setmPullListener(new ZRefreshLayout.PullListener() {
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
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.autoRefresh();
            }
        });
    }
}
