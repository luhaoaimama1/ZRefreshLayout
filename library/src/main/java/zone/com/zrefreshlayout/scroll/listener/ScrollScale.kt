package zone.com.zrefreshlayout.scroll.listener

import androidx.core.view.ViewCompat
import zone.com.zrefreshlayout.AUtils
import zone.com.zrefreshlayout.IScroll
import zone.com.zrefreshlayout.ZRefreshLayout

//zone todo 设计成 addScroll的模式；

class ScrollScale(mZRefreshLayout: ZRefreshLayout) : IScroll(mZRefreshLayout) {

    override fun scrollTo(fy: Int, isTriggerHeaderOnPullingDown: Boolean) {
        val contentHeight = AUtils.getContentHeight(mZRefreshLayout)
        val contentView = AUtils.getContentView(mZRefreshLayout)
        if (fy >= 0) {
            ViewCompat.setPivotY(contentView, 0f)
            ViewCompat.setScaleY(contentView, (contentHeight + fy) * 1f / contentHeight)
        } else {
            ViewCompat.setPivotY(contentView, contentHeight.toFloat())
            ViewCompat.setScaleY(contentView, (contentHeight - fy) * 1f / contentHeight)
        }
    }
}