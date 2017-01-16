package zone.com.zrefreshlayout;

/**
 * Created by fuzhipeng on 2017/1/16.
 */

public class AUtils {
    public static void setHeaderHeightToRefresh(ZRefreshLayout zRefreshLayout,int heightToRefresh) {
        zRefreshLayout.setHeaderHeightToRefresh(heightToRefresh);
    }
    public static void smoothScrollTo_NotIntercept(ZRefreshLayout.IScroll iScroll, int fy) {
        iScroll.smoothScrollTo_NotIntercept(fy);
    }
}
