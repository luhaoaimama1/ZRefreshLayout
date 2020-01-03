package zone.com.zrefreshlayout.loadmore;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.ZRefreshLayout;

import static zone.com.zrefreshlayout.utils.LogUtils.log;

/**
 * [2017] by Zone
 */

public class RecyclerViewLoadMoreListener implements LoadMoreOtherListener {

    private ZRefreshLayout zRefreshLayout;
    private OnScrollRecyclerViewListener mOnScrollRecyclerViewListener;

    @Override
    public void addListener(View view,ZRefreshLayout zRefreshLayout) {
        RecyclerView rv =(RecyclerView)view;
        this.zRefreshLayout=zRefreshLayout;
        rv.addOnScrollListener(mOnScrollRecyclerViewListener=new OnScrollRecyclerViewListener());
    }

    @Override
    public void removeListener(View view) {
        RecyclerView rv =(RecyclerView)view;
        rv.addOnScrollListener(mOnScrollRecyclerViewListener);
    }

    @Override
    public boolean haveListener() {
        return mOnScrollRecyclerViewListener!=null;
    }

    @Override
    public boolean instanceOf(View view) {
        return view instanceof RecyclerView;
    }

    @Override
    public LoadMoreOtherListener clone_() {
        return new RecyclerViewLoadMoreListener();
    }


    public  class OnScrollRecyclerViewListener extends RecyclerView.OnScrollListener {


        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            // 只有在闲置状态情况下检查
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 如果满足允许上拉加载、非加载状态中、最后一个显示的 item 与数据源的大小一样，则表示滑动入底部
                if (!isFirstItemVisible(recyclerView) && zRefreshLayout.isCanLoadMore()
                        && AUtils.isRest(zRefreshLayout) && isLastItemVisible(recyclerView)) {
                    log("RecyclerViewLoadMoreListener---->loadMore");
                    AUtils.loadMore(zRefreshLayout);// 执行上拉加载数据

                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        /**
         * 判断第一个条目是否完全可见
         *
         * @param recyclerView
         * @return
         */
        private boolean isFirstItemVisible(RecyclerView recyclerView) {
            final RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
            // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
            if (null == adapter || adapter.getItemCount() == 0) {
                return true;
            }
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition(recyclerView) == 0) {
                return recyclerView.getChildAt(0).getTop() >=0;
            }
            return false;
        }

        /**
         * 获取第一个可见子View的位置下标
         *
         * @param recyclerView
         * @return
         */
        private int getFirstVisiblePosition(RecyclerView recyclerView) {
            View firstVisibleChild = recyclerView.getChildAt(0);
            return firstVisibleChild != null ?
                    recyclerView.getChildAdapterPosition(firstVisibleChild) : -1;
        }

        /**
         * 判断最后一个条目是否完全可见
         *
         * @param recyclerView
         * @return
         */
        private boolean isLastItemVisible(RecyclerView recyclerView) {
            final RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
            // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
            if (null == adapter || adapter.getItemCount() == 0) {
                return true;
            }
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition(recyclerView);
            if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 1) {
                return recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom()
                        <= recyclerView.getBottom();
            }
            return false;
        }

        /**
         * 获取最后一个可见子View的位置下标
         *
         * @param recyclerView
         * @return
         */
        private int getLastVisiblePosition(RecyclerView recyclerView) {
            View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            return lastVisibleChild != null ? recyclerView.getChildAdapterPosition(lastVisibleChild) : -1;
        }
    }
}
