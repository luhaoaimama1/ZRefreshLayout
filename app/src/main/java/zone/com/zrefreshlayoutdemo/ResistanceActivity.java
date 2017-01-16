package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayoutdemo.resistance.Damping2Head;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class ResistanceActivity extends AppCompatActivity {
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
        refresh.setIResistance(new Damping2Head());
        tv.setText("下拉到头部后不可继续！");
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
    }
}
