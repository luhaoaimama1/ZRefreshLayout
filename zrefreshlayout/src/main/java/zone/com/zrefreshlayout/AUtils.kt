package zone.com.zrefreshlayout

import android.view.View

/**
 * Created by fuzhipeng on 2017/1/16.
 * 头部与footer的处理方法
 */
object AUtils {

    @JvmStatic
    fun setHeaderHeightToRefresh(zRefreshLayout: ZRefreshLayout, heightToRefresh: Int) {
        zRefreshLayout.setHeaderHeightToRefresh(heightToRefresh)
    }

    @JvmStatic
    fun smoothScrollTo_NotIntercept(iScroll: IScroll, fy: Int) {
        iScroll.smoothScrollTo_NotIntercept(fy)
    }

    @JvmStatic
    fun notityRefreshCompeleStateToRest(iScroll: IScroll) {
        iScroll.refreshCompeleStateToRest_AUtils()
    }

    /**
     * 真正的通知上拉并回调
     *
     * @param zRefreshLayout
     */
    @JvmStatic
    fun notityLoadMoreListener(zRefreshLayout: ZRefreshLayout) {
        zRefreshLayout.notityLoadMoreListener()
    }

    @JvmStatic
    fun notifyLoadMoreCompleteListener(zRefreshLayout: ZRefreshLayout) {
        zRefreshLayout.notifyLoadMoreCompleteListener()
    }

    @JvmStatic
    fun isRest(zRefreshLayout: ZRefreshLayout): Boolean {
        return zRefreshLayout.state == ZRefreshLayout.REST
    }

    /**
     * 自己处理底部动画,会走fooer的onstart,
     * 然后onstart里完成动画
     * 通知上啦回调[AUtils.notityLoadMoreListener]
     *
     * @param zRefreshLayout
     */
    @JvmStatic
    fun loadMore(zRefreshLayout: ZRefreshLayout) {
        zRefreshLayout.loadMore()
    }

    @JvmStatic
    fun getRefreshAbleHeight(zRefreshLayout: ZRefreshLayout): Int =  zRefreshLayout.getRefreshAbleHeight()


    @JvmStatic
    fun getContentHeight(zRefreshLayout: ZRefreshLayout): Int=zRefreshLayout.contentHeight

    @JvmStatic
    fun getContentView(zRefreshLayout: ZRefreshLayout): View =zRefreshLayout.contentView


}
