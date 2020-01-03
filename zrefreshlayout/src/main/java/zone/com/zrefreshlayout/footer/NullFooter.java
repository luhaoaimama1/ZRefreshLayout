package zone.com.zrefreshlayout.footer;

import android.view.View;

import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.IFooterView;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.LogUtils;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class NullFooter implements IFooterView {
    private View rootView;

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        rootView= new View(zRefreshLayout.getContext());
        return rootView;
    }

    @Override
    public void onStart(ZRefreshLayout zRefreshLayout, float footerHeight) {
        LogUtils.log(" AUtils.notityLoadMoreListener(zRefreshLayout);");
        AUtils.notityLoadMoreListener(zRefreshLayout);
    }

    @Override
    public void onComplete(ZRefreshLayout zRefreshLayout) {
        AUtils.notifyLoadMoreCompleteListener(zRefreshLayout);
    }

    @Override
    public IFooterView clone_() {
        NullFooter clone =new NullFooter();
        return clone;
    }
}
