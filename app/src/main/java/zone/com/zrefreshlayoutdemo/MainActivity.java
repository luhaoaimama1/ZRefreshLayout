package zone.com.zrefreshlayoutdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayoutdemo.common.Constant;
import zone.com.zrefreshlayoutdemo.common.HeadSetting;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.textView, R.id.listView, R.id.recyclerView, R.id.tv_mode, R.id.globalView,
            R.id.gridView, R.id.scrollerView, R.id.webView,
            R.id.autoRefresh, R.id.pinContent, R.id.config,
            R.id.uniqueFeature, R.id.refreshAblePosition, R.id.resistance,
            R.id.waveHeader, R.id.circleHeader,
            R.id.sinaRefresh, R.id.meterialHeader,
            R.id.cirlcleActivity, R.id.nestViewActivity,
            R.id.nestScroller,
            R.id.flexibilityListViewActivity, R.id.nestedScrollingActivity_Parent
    })
    public void onClick(View view) {
        if(view.getId()== R.id.globalView)
            Apps.setGlobalHead(SP1.INSTANCE.get(Constant.REFRESH_MODE,HeadSetting.class));
        else
            Apps.setDefaultHeader();

        switch (view.getId()) {
            case R.id.globalView:
                startActivity(new Intent(this, GlobalViewActivity.class));
                break;
            case R.id.tv_mode:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.textView:
                startActivity(new Intent(this, TextViewActivity.class));
                break;
            case R.id.listView:
                startActivity(new Intent(this, ListViewActivity.class));
                break;
            case R.id.recyclerView:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.gridView:
                startActivity(new Intent(this, GridViewActivity.class));
                break;
            case R.id.scrollerView:
                startActivity(new Intent(this, ScrollerViewActivity.class));
                break;
            case R.id.webView:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.autoRefresh:
                startActivity(new Intent(this, AutoRefreshActivity.class));
                break;
            case R.id.pinContent:
                startActivity(new Intent(this, PinContentActivity.class));
                break;
            case R.id.uniqueFeature:
                startActivity(new Intent(this, UniqueFeatureActivity.class));
                break;
            case R.id.refreshAblePosition:
                startActivity(new Intent(this, RefreshPositionActivity.class));
                break;
            case R.id.resistance:
                startActivity(new Intent(this, ResistanceActivity.class));
                break;
            case R.id.waveHeader:
                startActivity(new Intent(this, WaveHeaderActivity.class));
                break;
            case R.id.circleHeader:
                startActivity(new Intent(this, CircleHeaderActivity.class));
                break;
            case R.id.sinaRefresh:
                startActivity(new Intent(this, SinaHeaderActivity.class));
                break;
            case R.id.meterialHeader:
                startActivity(new Intent(this, MeterialHeaderActivity.class));
                break;
            case R.id.cirlcleActivity:
                startActivity(new Intent(this, CirlcleActivity.class));
                break;
            case R.id.nestViewActivity:
                startActivity(new Intent(this, NestViewActivity.class));
                break;
            case R.id.flexibilityListViewActivity:
                startActivity(new Intent(this, FlexibilityListViewActivity.class));
                break;
            case R.id.nestedScrollingActivity_Parent:
                startActivity(new Intent(this, NestedScrollingActivity_Parent.class));
                break;
            case R.id.nestScroller:
                startActivity(new Intent(this, ScrollerNesttActivity.class));
                break;
        }
    }

}
