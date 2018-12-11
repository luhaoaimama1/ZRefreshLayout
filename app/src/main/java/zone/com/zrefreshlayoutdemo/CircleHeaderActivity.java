package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayoutdemo.header.CircleRefresh;
import zone.com.zrefreshlayoutdemo.resistance.Damping2Head8per;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class CircleHeaderActivity extends AppCompatActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.refresh)
    ZRefreshLayout refresh;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unique_feature);
        ButterKnife.bind(this);

        refresh.setIHeaderView(new CircleRefresh());
//        refresh.setIResistance(new Damping2Head8per());

        tv.setText("包含滚动拦截后,准备就绪后,在滚动到特定位置! 还有下拉位置映射，刷新位置更改");
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

        }, new ZRefreshLayout.PullStateRestListener() {
            @Override
            public void refreshStateRestComplete(ZRefreshLayout zRefreshLayout) {
                iv.setImageResource(R.drawable.aaaaaaaaaaaab);
            }
        });
    }
}
