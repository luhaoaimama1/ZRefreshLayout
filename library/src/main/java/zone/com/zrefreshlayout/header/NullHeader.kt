package zone.com.zrefreshlayout.header

import android.view.View
import zone.com.zrefreshlayout.IHeaderView
import zone.com.zrefreshlayout.IScroll
import zone.com.zrefreshlayout.ScrollAnimation
import zone.com.zrefreshlayout.ZRefreshLayout

/**
 *[2018/12/12] by Zone
 */

class NullHeader : IHeaderView {

    private lateinit var rootView: View

    override fun getView(): View = rootView

    override fun initView(zRefreshLayout: ZRefreshLayout): View {
        rootView = View(zRefreshLayout.context)
        return rootView
    }

    override fun interceptAnimateBack(scrollAnimation: ScrollAnimation?, iScroll: IScroll?): Boolean =
        false

    override fun clone_(): IHeaderView = NullHeader()

    override fun onPullingDown(fraction: Float, headHeight: Float) {
    }

    override fun refreshAble(refreshAble: Boolean) {
    }

    override fun animateBack(scrollAnimation: ScrollAnimation?, fraction: Float, headHeight: Float, isPinContent: ZRefreshLayout.HeadPin) {
    }

    override fun onRefreshing(headHeight: Float, isAutoRefresh: Boolean) {
    }

    override fun onComplete() {
    }


}