package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.web.*
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web)
        rotate_header_web_view.settings.javaScriptEnabled = true
        rotate_header_web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                refresh.refreshComplete()
            }
        }
        refresh.pullListener = ZRefreshLayout.PullListener { rotate_header_web_view.loadUrl("http://luhaoaimama1.github.io") }

        refresh.loadMoreListener = ZRefreshLayout.LoadMoreListener {
            refresh.postDelayed({
                Toast.makeText(this@WebViewActivity, "load", Toast.LENGTH_SHORT).show()
                refresh.loadMoreComplete()
            }, 2000)
        }
    }
}
