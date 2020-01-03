package zone.com.zrefreshlayout.loadmore;

import android.view.View;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public interface LoadMoreOtherListener {

    void addListener(View view, ZRefreshLayout zRefreshLayout);
    void removeListener(View view);
    boolean haveListener();
    boolean instanceOf(View view);
    LoadMoreOtherListener clone_();
}
