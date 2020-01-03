package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.unique_feature.*
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class UniqueFeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unique_feature)
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
    }
}
