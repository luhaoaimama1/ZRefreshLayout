package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.lib.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;


public class NestedScrollingActivity_Parent extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    ZRefreshLayout refresh;

    private List<String> mDatas = new ArrayList<String>();
    private QuickRcvAdapter<String> adapter2;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_nested_parent);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        for (int i = 0; i < 50; i++) {
            mDatas.add("Parent Demo -> " + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter2=new QuickRcvAdapter<String>(this, mDatas) {
            @Override
            public void fillData(Helper<String> helper, String item, boolean itemChanged, int layoutId) {
                helper.setText(R.id.tv, item);
            }

            @Override
            public int getItemLayoutId(String s, int position) {
                return R.layout.item_rc_textview;
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
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                recyclerView.smoothScrollToPosition(0);
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
    }
   int  i=0;

    @Override
    public void setListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
