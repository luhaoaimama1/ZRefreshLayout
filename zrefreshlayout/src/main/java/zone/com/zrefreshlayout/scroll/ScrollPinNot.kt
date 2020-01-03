package zone.com.zrefreshlayout.scroll

import zone.com.zrefreshlayout.AUtils
import zone.com.zrefreshlayout.IScroll
import zone.com.zrefreshlayout.ZRefreshLayout

class ScrollPinNot(mZRefreshLayout: ZRefreshLayout) : IScroll(mZRefreshLayout) {

    override fun scrollTo(fy: Int, isTriggerHeaderOnPullingDown: Boolean) {
        if (mZRefreshLayout.iHeaderView != null && isTriggerHeaderOnPullingDown) {
            val refreshAbleHeight = AUtils.getRefreshAbleHeight(mZRefreshLayout)
            mZRefreshLayout.iHeaderView.onPullingDown(1f * Math.abs(fy) / refreshAbleHeight, refreshAbleHeight.toFloat())
        }
        mZRefreshLayout.scrollTo(0, -fy)
    }

    override fun getScrollY(): Int {
        return -mZRefreshLayout.scrollY
    }
}