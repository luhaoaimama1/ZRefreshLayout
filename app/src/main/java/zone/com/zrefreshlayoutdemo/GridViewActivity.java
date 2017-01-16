package zone.com.zrefreshlayoutdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.header.SinaRefreshHeader;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class GridViewActivity extends AppCompatActivity {
    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    private int i = 0;

    private List<String> mDatas = new ArrayList<String>();
    private QuickAdapter<String> adapter2;
    final int[] colorArry = {Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN};
    {
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        ButterKnife.bind(this);
        refresh.setIHeaderView(new SinaRefreshHeader());
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add("刷新完毕:" + i++);
                        adapter2.notifyDataSetChanged();
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void complete(ZRefreshLayout zRefreshLayout) {

            }
        });

        adapter2 = new QuickAdapter<String>(this, mDatas) {
            @Override
            public void fillData(final Helper<String> helper, final String item, boolean itemChanged, int layoutId) {
                helper.setText(R.id.tv, item)
                        .setBackgroundColor(R.id.tv, colorArry[helper.getPosition() % colorArry.length]);
            }

            @Override
            public int getItemLayoutId(String data, int position) {
                return R.layout.item_menu;
            }

        };
        gridView.setAdapter(adapter2);

    }
}
