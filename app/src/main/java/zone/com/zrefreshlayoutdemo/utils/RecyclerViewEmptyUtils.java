package zone.com.zrefreshlayoutdemo.utils;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class RecyclerViewEmptyUtils {

    private View emptyView;
    private RecyclerView rv;

    public static void addEmptyView(RecyclerView rv, @NonNull View emptyView) {
        RecyclerViewEmptyUtils recyclerViewEmptyUtils = new RecyclerViewEmptyUtils();
        recyclerViewEmptyUtils.addEmptyViewInner(rv, emptyView);
    }

    public static void addEmptyView(RecyclerView rv, @LayoutRes int layoutID) {
        RecyclerViewEmptyUtils recyclerViewEmptyUtils = new RecyclerViewEmptyUtils();
        recyclerViewEmptyUtils.addEmptyViewInner(rv, View.inflate(rv.getContext(), layoutID, null));
    }

    private void addEmptyViewInner(final RecyclerView rv, @NonNull final View emptyView) {
        RecyclerView.Adapter adapter = rv.getAdapter();
        if (adapter == null)
            throw new IllegalStateException("oldAdapter 不能为Null!");
        this.emptyView = emptyView;
        this.rv = rv;
        try {
            adapter.unregisterAdapterDataObserver(observer);
        } catch (Exception e) {
        }
        if (rv.getParent() instanceof ZRefreshLayout) {
            ZRefreshLayout parent = (ZRefreshLayout) rv.getParent();
            parent.addView(emptyView, 1);
        } else
            throw new IllegalStateException("rv parent 类型必须是ZRefreshLayout!");
        adapter.registerAdapterDataObserver(observer);
        checkIfEmpty();
    }

//    private void replaceView(RecyclerView rv, @NonNull View emptyView) {
//        ViewGroup parent = (ViewGroup) rv.getParent();
//        FrameLayout frameLayout = new FrameLayout(rv.getContext());
//        frameLayout.setLayoutParams(rv.getLayoutParams());
//        int index = parent.indexOfChild(rv);
//
//        parent.removeView(rv);
//        frameLayout.addView(rv);
//        frameLayout.addView(emptyView);
//        parent.addView(frameLayout, index);
//    }


    private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        public String TAG = "RecyclerViewUtils";

        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            Log.i(TAG, "onItemRangeInserted" + itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };


    private void checkIfEmpty() {
        boolean emptyViewVisible =
                rv.getAdapter().getItemCount() == 0;
        emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
    }

}
