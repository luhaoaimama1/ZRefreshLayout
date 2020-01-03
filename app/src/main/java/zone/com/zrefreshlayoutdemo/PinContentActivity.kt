package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.auto_refresh.*
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class PinContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auto_refresh)
        refresh.headPin = ZRefreshLayout.HeadPin.PIN
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
    }
}
