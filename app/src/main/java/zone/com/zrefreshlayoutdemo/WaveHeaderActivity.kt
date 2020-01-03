package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.unique_feature.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayoutdemo.header.WaveHead

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class WaveHeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unique_feature)
        val waveHead = WaveHead()
        waveHead.resourceId = R.drawable.wave
        refresh.iHeaderView = waveHead

        tv.text = "写着玩的没啥用~"
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
    }
}
