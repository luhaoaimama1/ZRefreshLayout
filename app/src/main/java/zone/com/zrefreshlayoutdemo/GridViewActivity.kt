package zone.com.zrefreshlayoutdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridView
import java.util.ArrayList

import butterknife.ButterKnife
import kotlinx.android.synthetic.main.grid.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayoutdemo.delegate.AdapterList

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class GridViewActivity : AppCompatActivity() {
    private var i = 0

    private val mDatas = ArrayList<String>()
    private var adapter2: AdapterList? = null

    init {
        run {
            var i: Int = 'A'.toInt()
            while (i <= 'Z'.toInt()) {
                mDatas.add("" + i.toChar())
                i++
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grid)
        ButterKnife.bind(this)
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            refresh.postDelayed({
                mDatas.add("刷新完毕:" + i++)
                adapter2!!.notifyDataSetChanged()
                zRefreshLayout.refreshComplete()
            }, 2000)
        }
        refresh.loadMoreListener = ZRefreshLayout.LoadMoreListener { zRefreshLayout ->
            refresh.postDelayed({
                mDatas.add("加载完毕:" + i++)
                adapter2!!.notifyDataSetChanged()
                zRefreshLayout.loadMoreComplete()
            }, 2000)
        }

        gridView!!.adapter = AdapterList(this, mDatas)

    }
}
