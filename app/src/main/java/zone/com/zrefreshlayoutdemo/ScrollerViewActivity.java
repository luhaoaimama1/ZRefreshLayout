package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.resistance.Damping2;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class ScrollerViewActivity extends AppCompatActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroller);
        ButterKnife.bind(this);

        initText();

        refresh.setIResistance(new Damping2());
        refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("TV1:加载次数" + i);
                        tv2.setText("TV2:加载次数" + i);
                        tv3.setText("TV3:加载次数" + i);
                        zRefreshLayout.loadMoreComplete();
                    }
                }, 2000);

            }

            @Override
            public void complete(ZRefreshLayout zRefreshLayout) {

            }
        });
        refresh.setmPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        initText();
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void complete(ZRefreshLayout zRefreshLayout) {

            }
        });
    }

    private void initText() {
        tv1.setText("TV1:刷新次数" + i);
        tv2.setText("TV2:刷新次数" + i);
        tv3.setText("TV3:刷新次数" + i);
    }
}
