package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.unique_feature_copy.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.header.MeterialHead

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class AutoRefreshActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unique_feature_copy)
        refresh.iHeaderView = MeterialHead()
        refresh.headPin = ZRefreshLayout.HeadPin.PIN
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                iv.setImageResource(R.drawable.aaaaaaaaaaaab)
                zRefreshLayout.refreshComplete()
            }, 2000)
        }

    }

   override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv -> refresh.postDelayed({ refresh.autoRefresh(false) }, 1000)
            R.id.tvAnimate -> refresh.postDelayed({ refresh.autoRefresh(true) }, 1000)
            else->{}
        }
    }
}
