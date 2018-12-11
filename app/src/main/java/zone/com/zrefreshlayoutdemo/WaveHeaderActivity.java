package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayoutdemo.header.WaveHead;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class WaveHeaderActivity extends AppCompatActivity {
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

        WaveHead waveHead = new WaveHead();
        waveHead.setResourceId(R.drawable.wave);
        refresh.setIHeaderView(waveHead);

        tv.setText("写着玩的没啥用~");
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
    }
}
