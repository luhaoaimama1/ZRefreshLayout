package zone.com.zrefreshlayout.scroll

import zone.com.zrefreshlayout.IScroll
import zone.com.zrefreshlayout.ZRefreshLayout

class ScrollDoNothing(mZRefreshLayout: ZRefreshLayout) : IScroll(mZRefreshLayout) {
    var scrollFy: Int = 0
    override fun scrollTo(fy: Int, isTriggerHeaderOnPullingDown: Boolean) {
        scrollFy = fy
    }

    override fun getScrollY(): Int = scrollFy
}

