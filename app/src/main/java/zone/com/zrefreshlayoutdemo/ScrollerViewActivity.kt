package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.scroller.*
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class ScrollerViewActivity : AppCompatActivity() {
    internal var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroller)

        initText()
        refresh.loadMoreListener = ZRefreshLayout.LoadMoreListener { zRefreshLayout ->
            refresh.postDelayed({
                tv1.text = "TV1:加载次数$i"
                tv2.text = "TV2:加载次数$i"
                tv3.text = "TV3:加载次数$i"
                zRefreshLayout.loadMoreComplete()
            }, 2000)
        }
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                i++
                initText()
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
    }

    private fun initText() {
        tv1.text = "TV1:刷新次数$i"
        tv2.text = "TV2:刷新次数$i"
        tv3.text = "TV3:刷新次数$i"
    }
}
