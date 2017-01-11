package zone.com.zrefreshlayout.header;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import zone.com.zrefreshlayout.IHeaderView;
import zone.com.zrefreshlayout.R;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * Created by lcodecore on 2016/10/2.
 */

public class SinaRefreshView   implements IHeaderView {

    private ImageView refreshArrow;
    private ImageView loadingView;
    private TextView refreshTextView;
    private View rootView;

    public void setArrowResource(@DrawableRes int resId) {
        refreshArrow.setImageResource(resId);
    }

    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    private String pullDownStr = "下拉刷新";
    private String releaseRefreshStr = "释放刷新";
    private String refreshingStr = "正在刷新";

    @Override
    public IHeaderView clone_() {
        SinaRefreshView clone =new SinaRefreshView();
        return clone;
    }

    @Override
    public View getView(Context context) {
        rootView = View.inflate(context, R.layout.view_sinaheader, null);
        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        return rootView;
    }

    @Override
    public void onPullingDown(float fraction, float headHeight) {
        if (fraction < 1f) refreshTextView.setText(pullDownStr);
        if (fraction > 1f) refreshTextView.setText(releaseRefreshStr);
        refreshArrow.setRotation(fraction * 180);
    }

    @Override
    public void onPullReleasing(float fraction, float headHeight) {
        if (fraction < 1f) {
//            refreshTextView.setText(pullDownStr);
//            refreshArrow.setRotation(fraction * 180);
//            if (refreshArrow.getVisibility() == GONE) {
//                refreshArrow.setVisibility(VISIBLE);
//                loadingView.setVisibility(GONE);
//            }
        }
    }

    @Override
    public void onRefreshing(float headHeight) {
        refreshTextView.setText(refreshingStr);
        refreshArrow.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        ((AnimationDrawable)loadingView.getDrawable()).start();
        ZRefreshLayout.log("onRefreshing");
    }

    @Override
    public void onComplete() {
        refreshArrow.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        ((AnimationDrawable)loadingView.getDrawable()).stop();
        ZRefreshLayout.log("onComplete");
    }


}
