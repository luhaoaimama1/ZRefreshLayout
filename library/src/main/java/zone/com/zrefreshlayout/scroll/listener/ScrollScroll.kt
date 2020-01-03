package zone.com.zrefreshlayout.scroll.listener

import androidx.core.view.ViewCompat
import zone.com.zrefreshlayout.AUtils
import zone.com.zrefreshlayout.IScroll
import zone.com.zrefreshlayout.ZRefreshLayout

//zone todo 设计成 addScroll的模式；

class ScrollScroll(mZRefreshLayout: ZRefreshLayout) : IScroll(mZRefreshLayout) {

    override fun scrollTo(fy: Int, isTriggerHeaderOnPullingDown: Boolean) {
        mZRefreshLayout.scrollTo(0, -fy)
    }
}