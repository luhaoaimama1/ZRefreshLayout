package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import java.util.ArrayList
import com.zone.adapter3.QuickRcvAdapter
import kotlinx.android.synthetic.main.a_flexibility.*
import kotlinx.android.synthetic.main.a_flexibility.refresh
import kotlinx.android.synthetic.main.a_flexibility.rv
import zone.com.zrefreshlayout.IScroll
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.scroll.listener.ScrollScale
import zone.com.zrefreshlayout.scroll.listener.ScrollScroll
import zone.com.zrefreshlayoutdemo.delegate.MenuEntityDeletates

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class FlexibilityKtActivity : AppCompatActivity(),View.OnClickListener {

    private val mDatas = ArrayList<String>()
    private var adapter2: QuickRcvAdapter<String>? = null
    var height: Int = 0

    init {
        var i: Int = 'A'.toInt()
        while (i <= 'H'.toInt()) {
            mDatas.add("" + i.toChar())
            i++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_flexibility)
        refresh!!.headPin=ZRefreshLayout.HeadPin.NOTHING
        iv.post { height = iv.height }
        rv!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        adapter2= QuickRcvAdapter<String>(this,mDatas).apply {
            addViewHolder(MenuEntityDeletates())
            relatedList(rv)
        }
    }


    override fun onClick(view: View?) {
        view?.let{
            val scrollScale = when (view.id) {
                R.id.bt_scroll -> arrayListOf(ScrollScroll(refresh!!))
                R.id.bt_scale -> arrayListOf(ScrollScale(refresh!!))
                R.id.bt_Header -> arrayListOf(ScrollHeadScroll(refresh!!, iv!!, height), ScrollScroll(refresh!!))
                else -> arrayListOf(ScrollScroll(refresh!!))
            }
            refresh!!.iScrollList.clear()
            refresh!!.iScrollList.addAll(scrollScale)
        }
    }

    class ScrollHeadScroll(mZRefreshLayout: ZRefreshLayout, val iv: ImageView, val height: Int) : IScroll(mZRefreshLayout) {

        override fun scrollTo(fy: Int, isTriggerHeaderOnPullingDown: Boolean) {
            if (fy > 0) {
                val ph = 1 + fy.toFloat() / height.toFloat()
                ViewCompat.setPivotY(iv, 0f)
                ViewCompat.setScaleX(iv, ph)
                ViewCompat.setScaleY(iv, ph)
            }
        }
    }
}

