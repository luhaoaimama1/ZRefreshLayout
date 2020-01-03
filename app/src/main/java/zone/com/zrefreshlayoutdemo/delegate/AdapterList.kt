package zone.com.zrefreshlayoutdemo.delegate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import zone.com.zrefreshlayoutdemo.R
import zone.com.zrefreshlayoutdemo.RecyclerViewActivity

class AdapterList(val context: Context, private val stuList: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return stuList.size
    }

    override fun getItem(position: Int): Any {
        return stuList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_menu, null)
        val tv = view.findViewById<TextView>(R.id.tv)
        tv.setText( stuList.get(position))
        val i = RecyclerViewActivity.colorArry[position %
                RecyclerViewActivity.colorArry.size]
        tv.setBackgroundColor(i)
        return view
    }
}