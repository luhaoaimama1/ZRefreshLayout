package zone.com.zrefreshlayout.footer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import zone.com.zrefreshlayout.AUtils;
import zone.com.zrefreshlayout.IFooterView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.utils.ScreenUtils;
import zone.com.zrefreshlayout.v4.MeterialCircle;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class MeterialFooter implements IFooterView {
    private MeterialCircle mMeterialCircle;
    private View rootView;

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        rootView = View.inflate(zRefreshLayout.getContext(), R.layout.header_meterial, null);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        LinearLayout ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.1)));
        mMeterialCircle=new MeterialCircle(ll_main,(int) (screenPixs[1] * 0.08));
        rootView.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @Override
    public void onStart(ZRefreshLayout zRefreshLayout, float footerHeight) {
        rootView.setVisibility(View.VISIBLE);
        mMeterialCircle.start();
        AUtils.notityLoadMoreListener(zRefreshLayout);
    }

    @Override
    public void onComplete(ZRefreshLayout zRefreshLayout) {
        rootView.setVisibility(View.INVISIBLE);
        mMeterialCircle.stop();
        AUtils.notifyLoadMoreCompleteListener(zRefreshLayout);

    }
    @Override
    public IFooterView clone_() {
        MeterialFooter clone =new MeterialFooter();
        return clone;
    }
}
