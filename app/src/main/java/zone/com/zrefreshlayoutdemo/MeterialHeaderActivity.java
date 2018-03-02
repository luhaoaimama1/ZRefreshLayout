package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zone.lib.utils.activity_fragment_ui.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.footer.MeterialFooter;
import zone.com.zrefreshlayout.header.MeterialHead;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class MeterialHeaderActivity extends AppCompatActivity {
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_refresh);
        ButterKnife.bind(this);

        refresh.setIHeaderView(new MeterialHead());

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

        });

        refresh.setIFooterView(new MeterialFooter());
        refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(MeterialHeaderActivity.this,"加载更多");
                        zRefreshLayout.loadMoreComplete();
                    }
                }, 2000);
            }

        });
    }
}
