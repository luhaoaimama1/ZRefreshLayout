package zone.com.zrefreshlayoutdemo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import kotlinx.android.synthetic.main.list.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayoutdemo.delegate.AdapterList

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class ListViewActivity : AppCompatActivity() {
    private var i = 0
    private val mDatas = ArrayList<String>()
    private var adapter2: AdapterList? = null
    init {
        run {
            var i: Int = 'A'.toInt()
            while (i <= 'H'.toInt()) {
                mDatas.add("" + i.toChar())
                i++
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)
        refresh.loadMoreListener = ZRefreshLayout.LoadMoreListener { zRefreshLayout ->
            refresh.postDelayed({
                mDatas.add("加载完毕:" + i++)
                adapter2!!.notifyDataSetChanged()
                zRefreshLayout.loadMoreComplete()
            }, 2000)
        }
        refresh.pullListener = ZRefreshLayout.PullListener { zRefreshLayout ->
            listView!!.smoothScrollToPosition(0)
            refresh.postDelayed({
                mDatas.add("刷新完毕:" + i++)
                adapter2!!.notifyDataSetChanged()
                zRefreshLayout.refreshComplete()
            }, 2000)
        }

        adapter2 = AdapterList(this, mDatas)
        listView.adapter = adapter2
    }

}
