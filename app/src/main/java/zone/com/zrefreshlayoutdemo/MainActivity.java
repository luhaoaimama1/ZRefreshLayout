package zone.com.zrefreshlayoutdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.Config;
import zone.com.zrefreshlayout.footer.MeterialFooter;
import zone.com.zrefreshlayout.header.MeterialHead;
import zone.com.zrefreshlayout.resistance.DampingHalf;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        int[] colors_red_green_yellow = new int[]{
                Color.parseColor("#ffF44336"),
                Color.parseColor("#ff4CAF50"),
                Color.parseColor("#ffFFEB3B")
        };
        Config.build()
                .setHeader(new MeterialHead(colors_red_green_yellow))
                .setFooter(new MeterialFooter())
                .setResistance(new DampingHalf())
//                .setHeader(new  CircleRefresh())
//                .setResistance(new Damping2Head8per())
                .writeLog(true)
                .perform();

    }

    @OnClick({R.id.textView, R.id.listView, R.id.recyclerView,
            R.id.gridView, R.id.scrollerView, R.id.webView,
            R.id.autoRefresh, R.id.pinContent, R.id.config,
            R.id.uniqueFeature, R.id.refreshAblePosition, R.id.resistance,
            R.id.waveHeader, R.id.circleHeader,
            R.id.sinaRefresh, R.id.meterialHeader,
            R.id.cirlcleActivity
            })
    public void onClick(View view) {
        switch (view.getId()) {
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
        }
    }

}
