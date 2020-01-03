package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.unique_feature.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.resistance.DampingHalfToHead

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class ResistanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unique_feature)
        refresh.iResistance = DampingHalfToHead()
        tv.text = "下拉到头部后不可继续！"
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
    }
}
