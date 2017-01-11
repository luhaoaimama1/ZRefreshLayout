package zone.com.zrefreshlayout;

import android.content.Context;
import android.view.View;

/**
 * Created by lcodecore on 2016/10/1.
 */

public interface IHeaderView {
    IHeaderView clone_();

    View getView(Context context);

    /**
     * 下拉准备刷新动作
     * @param fraction  当前下拉高度与头部高度的比  超过头部动画基本保持不变
     * @param headHeight
     */
    void onPullingDown(float fraction, float headHeight);

    /**
     * 下拉释放过程
     * @param fraction
     * @param headHeight
     */
    void onPullReleasing(float fraction,float headHeight);

    void onRefreshing(float headHeight);

    void onComplete();
}
