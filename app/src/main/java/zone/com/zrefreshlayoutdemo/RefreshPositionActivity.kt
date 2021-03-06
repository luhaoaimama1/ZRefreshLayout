package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.unique_feature.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayoutdemo.header.SinaRefreshViewRefreshPositionTest

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class RefreshPositionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unique_feature)
        tv.text = "下拉到头部一半,则可刷新！"
        refresh.setIHeaderView(SinaRefreshViewRefreshPositionTest())
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
    }
}
