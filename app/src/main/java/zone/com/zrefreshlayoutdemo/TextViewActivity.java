package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class TextViewActivity extends AppCompatActivity {
    private ZRefreshLayout refresh;
    private TextView tv;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        refresh = (ZRefreshLayout) findViewById(R.id.refresh);
        refresh.setPinContent(true);
//        refresh.setIHeaderView(new MeterialHead());


//        refresh.setPinContent(true);
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
                }, 500);
            }

            @Override
            public void loadMoreAnimationComplete(ZRefreshLayout zRefreshLayout) {

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
                }, 500);
            }

            @Override
            public void refreshAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });
//        refresh.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refresh.autoRefresh();
//            }
//        },2000);
    }
}
