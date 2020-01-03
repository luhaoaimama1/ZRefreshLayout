package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import kotlinx.android.synthetic.main.auto_refresh.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.footer.MeterialFooter
import zone.com.zrefreshlayout.header.MeterialHead

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class MeterialHeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auto_refresh)
        refresh.iHeaderView = MeterialHead()

        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }

        refresh.iFooterView = MeterialFooter()
        refresh.loadMoreListener = ZRefreshLayout.LoadMoreListener { zRefreshLayout ->
            refresh.postDelayed({
                ToastUtils.showShort(this@MeterialHeaderActivity, "加载更多")
                zRefreshLayout.loadMoreComplete()
            }, 2000)
        }
    }
}
