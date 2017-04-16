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
        iScroll.refreshCompeleStateToRest();
    }
    public static void notityLoadMoreListener(ZRefreshLayout zRefreshLayout){
        zRefreshLayout.notityLoadMoreListener();
    }
    public static void notifyLoadMoreCompleteListener(ZRefreshLayout zRefreshLayout){
        zRefreshLayout.notifyLoadMoreCompleteListener();
    }
}
