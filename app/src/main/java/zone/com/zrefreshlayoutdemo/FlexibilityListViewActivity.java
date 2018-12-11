package zone.com.zrefreshlayoutdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.nineoldandroids.view.ViewHelper;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.ZFlexibilityLayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class FlexibilityListViewActivity extends AppCompatActivity {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.refresh)
    ZFlexibilityLayout refresh;
    @BindView(R.id.iv)
    ImageView iv;
    private List<String> mDatas = new ArrayList<String>();
    private QuickAdapter<String> adapter2;
    final int[] colorArry = {Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN};

    {
        for (int i = 'A'; i <= 'H'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    public int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flexibility_list);
        ButterKnife.bind(this);
        iv.post(new Runnable() {

            @Override
            public void run() {
                height = iv.getHeight();
            }
        });
        refresh.setIFlexScroll(new ZFlexibilityLayout.IFlexScroll() {
            @Override
            public void scroll(int contentHeight, int headerHeight, int offset) {
                if (refresh.getType() == ZFlexibilityLayout.Type.Header) {
                    if (offset > 0) {
                        float ph = 1 + (float) offset / (float) height;
                        ViewCompat.setPivotY(iv, 0);
                        ViewCompat.setScaleX(iv, ph);
                        ViewCompat.setScaleY(iv, ph);

//                        System.out.println("offset:"+offset);
//                        ViewGroup.LayoutParams lp= iv.getLayoutParams();
//                        lp.height= height+offset;
//                        iv.setLayoutParams(lp);
                    }
                }
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
        listView.setAdapter(adapter2);
    }


    @OnClick({R.id.bt_scroll, R.id.bt_scale, R.id.bt_Header})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_scroll:
                refresh.setType(ZFlexibilityLayout.Type.Scroll);
                break;
            case R.id.bt_scale:
                refresh.setType(ZFlexibilityLayout.Type.Scale);
                break;
            case R.id.bt_Header:
                refresh.setType(ZFlexibilityLayout.Type.Header);
                break;
        }
    }
}
