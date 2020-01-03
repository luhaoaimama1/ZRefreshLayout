package zone.com.zrefreshlayoutdemo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import com.zone.adapter3.QuickRcvAdapter
import kotlinx.android.synthetic.main.a_scroll_recycler_nest.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayoutdemo.delegate.MenuEntityDeletates

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class ScrollerNesttActivity : AppCompatActivity() {
    private var i = 0
    private val mDatas = ArrayList<String>()
    private var adapter2: QuickRcvAdapter<String>? = null

    init {
        run {
            var i: Int = 'A'.toInt()
            while (i <= 'T'.toInt()) {
                mDatas.add("" + i.toChar())
                i++
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_scroll_recycler_nest)
        setSupportActionBar(id_toolbar)

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
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        adapter2= QuickRcvAdapter<String>(this,mDatas).apply {
            addViewHolder(MenuEntityDeletates())
            relatedList(rv)
        }
    }

}
