package zone.com.zrefreshlayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.Config;
import zone.com.zrefreshlayout.footer.FooterView;
import zone.com.zrefreshlayout.header.SinaRefreshView;
import zone.com.zrefreshlayout.resistance.Damping2;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        Config.build().setHeader(new SinaRefreshView())
                .setFooter(new FooterView())
                .setResistance(new Damping2())
                .writeLog(true)
                .perform();
    }

    @OnClick({R.id.textView, R.id.listView, R.id.recyclerView,
            R.id.gridView, R.id.scrollerView, R.id.webView,
            R.id.autoRefresh, R.id.pinContent, R.id.config,
            R.id.uniqueFeature
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
                startActivity(new Intent(this, HeaderFixActivity.class));
                break;
            case R.id.uniqueFeature:
                startActivity(new Intent(this, UniqueFeatureActivity.class));
                break;
        }
    }

}
