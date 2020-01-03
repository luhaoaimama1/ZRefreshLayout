package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class GlobalViewActivity extends AppCompatActivity {
    private ZRefreshLayout refresh;
    private TextView tv;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        refresh = (ZRefreshLayout) findViewById(R.id.refresh);
        tv = (TextView) findViewById(R.id.tv);
        refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                tv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("加载更多:" + i++);
                        zRefreshLayout.loadMoreComplete();
                    }
                }, 2000);
            }


        });
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                tv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("刷新完毕:" + i++);
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

        });
    }
}
