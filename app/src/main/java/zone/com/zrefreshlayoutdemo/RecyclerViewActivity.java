package zone.com.zrefreshlayoutdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zone.adapter.QuickRcvAdapter;
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

public class RecyclerViewActivity extends AppCompatActivity {
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    private int i = 0;
    private List<String> mDatas = new ArrayList<String>();
    private QuickRcvAdapter<String> adapter2;
    final int[] colorArry = {Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN};

    {
        for (int i = 'A'; i <= 'H'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rev);
        ButterKnife.bind(this);
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

        });
        refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add("加载完毕:" + i++);
                        adapter2.notifyDataSetChanged();
                        zRefreshLayout.loadMoreComplete();
                    }
                }, 2000);

            }

        });
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter2 = new QuickRcvAdapter<String>(this, mDatas) {
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
        rv.setAdapter(adapter2);
    }
}
