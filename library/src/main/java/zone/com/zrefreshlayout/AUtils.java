package zone.com.zrefreshlayout;

/**
 * Created by fuzhipeng on 2017/1/16.
 * 头部与footer的处理方法
 */
public class AUtils {
    
    public static void setHeaderHeightToRefresh(ZRefreshLayout zRefreshLayout, int heightToRefresh) {
        zRefreshLayout.setHeaderHeightToRefresh(heightToRefresh);
    }

    public static void smoothScrollTo_NotIntercept(ZRefreshLayout.IScroll iScroll, int fy) {
        iScroll.smoothScrollTo_NotIntercept(fy);
    }

    public static void notityRefreshCompeleStateToRest(ZRefreshLayout.IScroll iScroll) {
        iScroll.refreshCompeleStateToRest_AUtils();
    }

    /**
     * 真正的通知上拉并回调
     *
     * @param zRefreshLayout
     */
    public static void notityLoadMoreListener(ZRefreshLayout zRefreshLayout) {
        zRefreshLayout.notityLoadMoreListener();
    }

    public static void notifyLoadMoreCompleteListener(ZRefreshLayout zRefreshLayout) {
        zRefreshLayout.notifyLoadMoreCompleteListener();
    }


    public static boolean isRest(ZRefreshLayout zRefreshLayout) {
        return zRefreshLayout.state == zRefreshLayout.REST;
    }

    /**
     * 自己处理底部动画,会走fooer的onstart,
     * 然后onstart里完成动画
     * 通知上啦回调{@link AUtils#notityLoadMoreListener(ZRefreshLayout)}
     *
     * @param zRefreshLayout
     */
    public static void loadMore(ZRefreshLayout zRefreshLayout) {
        zRefreshLayout.loadMore();
    }


}
