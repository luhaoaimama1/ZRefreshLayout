package zone.com.zrefreshlayout.loadmore;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class LoadMoreController {
    public static List<LoadMoreOtherListener> list = new ArrayList<>();

    static {
        list.add(new RecyclerViewLoadMoreListener());
        list.add(new ListViewLoadMoreListener());
    }

    public static LoadMoreOtherListener addLoadMoreListener(View view, ZRefreshLayout zRefreshLayout) {
        LoadMoreOtherListener result = null;
        for (LoadMoreOtherListener loadMoreListener : list) {
            if (loadMoreListener.instanceOf(view)) {
                result = loadMoreListener.clone_();
                result.addListener(view, zRefreshLayout);
                break;
            }
        }
        return result;
    }
}
