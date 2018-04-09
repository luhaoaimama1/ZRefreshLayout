package zone.com.zrefreshlayout.loadmore;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class  LoadMoreController {

    private static List<LoadMoreOtherListener> list = new ArrayList<>();

    static {
        list.add(new RecyclerViewLoadMoreListener());
        list.add(new ListViewLoadMoreListener());
    }

    public static LoadMoreOtherListener addLoadMoreListener(View view, ZRefreshLayout zRefreshLayout) {
        LoadMoreOtherListener result = null;
        for (LoadMoreOtherListener loadMoreListener : list) {
            if (loadMoreListener.instanceOf(view)) {
                //这里用深克隆  所以不会内存泄漏
                result = loadMoreListener.clone_();
                result.addListener(view, zRefreshLayout);
                break;
            }
        }
        return result;
    }

    public static List<LoadMoreOtherListener> getList() {
        return list;
    }

    public static void setList(List<LoadMoreOtherListener> list) {
        LoadMoreController.list = list;
    }
}
