package zone.com.zrefreshlayout.loadmore;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.ZRefreshLayout;
import static zone.com.zrefreshlayout.utils.LogUtils.log;

/**
 * [2017] by Zone
 */

public class ListViewLoadMoreListener implements LoadMoreOtherListener {

    private ZRefreshLayout zRefreshLayout;
    private OnScrollListViewListener mOnScrollRecyclerViewListener;

    @Override
    public void addListener(View view, ZRefreshLayout zRefreshLayout) {
        ListView rv = (ListView) view;
        this.zRefreshLayout = zRefreshLayout;
        rv.setOnScrollListener(mOnScrollRecyclerViewListener = new OnScrollListViewListener());
    }

    @Override
    public void removeListener(View view) {
        ListView rv = (ListView) view;
        rv.setOnScrollListener(null);
    }

    @Override
    public boolean haveListener() {
        return mOnScrollRecyclerViewListener != null;
    }

    @Override
    public boolean instanceOf(View view) {
        return view instanceof ListView;
    }

    @Override
    public LoadMoreOtherListener clone_() {
        return new ListViewLoadMoreListener();
    }


    public class OnScrollListViewListener implements AbsListView.OnScrollListener {


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                if (!isFirstItemVisible(view) && zRefreshLayout.isCanLoadMore()
                        && AUtils.isRest(zRefreshLayout) && isLastItemVisible(view)) {
                    log("ListViewLoadMoreListener---->loadMore");
                    AUtils.loadMore(zRefreshLayout);// 执行上拉加载数据
                }
        }

        /**
         * 判断最后一个条目是否完全可见
         *
         * @param view
         * @return
         */
        private boolean isLastItemVisible(AbsListView view) {
            ListAdapter adapter = view.getAdapter();
            // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
            if (null == adapter || adapter.getCount() == 0) {
                return true;
            }
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = view.getLastVisiblePosition();
            if (lastVisiblePosition >= adapter.getCount() - 1) {
                return view.getChildAt(view.getChildCount() - 1).getBottom()
                        <= view.getBottom();
            }
            return false;
        }

        private boolean isFirstItemVisible(AbsListView view) {
            ListAdapter adapter = view.getAdapter();
            if (null == adapter || adapter.getCount() == 0)
                return true;
            if (view.getFirstVisiblePosition() == 0)
                return view.getChildAt(0).getTop() >= view.getTop();
            return false;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }


    }
}
