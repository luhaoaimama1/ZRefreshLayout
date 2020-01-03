package zone.com.zrefreshlayoutdemo.delegate

import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates
import zone.com.zrefreshlayoutdemo.R
import zone.com.zrefreshlayoutdemo.RecyclerViewActivity

class MenuEntityDeletates() : ViewDelegates<String>() {
    override fun getLayoutId(): Int = R.layout.item_menu

    override fun fillData(posi: Int, entity: String?, holder: Holder<out Holder<*>>?) {
        holder?.setText(R.id.tv, entity)
                ?.setBackgroundColor(R.id.tv, RecyclerViewActivity.colorArry[holder.getAdapterPosition() % RecyclerViewActivity.colorArry.size])
    }

}