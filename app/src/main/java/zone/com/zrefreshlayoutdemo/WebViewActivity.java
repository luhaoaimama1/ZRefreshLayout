package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.rotate_header_web_view)
    WebView mWebView;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
        ButterKnife.bind(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                refresh.refreshComplete();
            }
        });
        refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                mWebView.loadUrl("http://luhaoaimama1.github.io");
            }

            @Override
            public void refreshAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });

        refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebViewActivity.this, "load", Toast.LENGTH_SHORT).show();
                        refresh.loadMoreComplete();
                    }
                }, 2000);

            }

            @Override
            public void loadMoreAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });
    }
}
