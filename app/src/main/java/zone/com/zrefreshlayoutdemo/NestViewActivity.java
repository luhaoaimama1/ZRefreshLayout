package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.callback.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZFlexibilityLayout;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayoutdemo.views.ZNestParentLayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class NestViewActivity extends AppCompatActivity {

    @BindView(R.id.refresh)
    ZRefreshLayout refresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.zpl)
    ZNestParentLayout zpl;
    @BindView(R.id.indicatorView)
    TextView indicatorView;
    @BindView(R.id.iv)
    ImageView iv;

    public int height;

    private List<String> mDatas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nest);
        ButterKnife.bind(this);
        iv.post(new Runnable() {

            @Override
            public void run() {
                height = iv.getHeight();
            }
        });
        zpl.setIFlexScroll(new ZNestParentLayout.IFlexScroll() {
            @Override
            public void scroll(int offset) {
                System.out.println("ZNestParentLayout  offset==>"+offset);
//                ViewGroup.LayoutParams lp = iv.getLayoutParams();
//                lp.height = height + offset;
//                iv.setLayoutParams(lp);
                zpl.scrollTo(0,-offset);
//                recyclerView.offsetTopAndBottom(-offset);
            }
        });
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
            }

        });
        for (int i = 0; i < 50; i++) {
            mDatas.add("Parent Demo -> " + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new QuickRcvAdapter<String>(this, mDatas) {
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
                        Toast.makeText(NestViewActivity.this, "load", Toast.LENGTH_SHORT).show();
                        refresh.loadMoreComplete();
                    }
                }, 2000);

            }

        });
        refresh.setCanLoadMore(false);
        refresh.setCanRefresh(false);
    }
}
