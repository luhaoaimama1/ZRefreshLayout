package zone.com.zrefreshlayout;

import androidx.annotation.NonNull;
import android.view.View;

/**
 * Created by fuzhipeng on 2017/1/16.
 */

public interface IHeaderView {


    /**
     * 获取头部布局
     *
     * @param zRefreshLayout
     * @return
     */
    View initView(ZRefreshLayout zRefreshLayout);

    @NonNull
    View getView();

    /**
     * 下拉准备刷新动作
     *
     * @param fraction   当前下拉高度与头部高度的比  超过头部动画基本保持不变
     * @param headHeight
     */
    void onPullingDown(float fraction, float headHeight);

    /**
     * 可刷新与不可刷新 状态切换监听
     *
     * @param refreshAble
     */
    void refreshAble(boolean refreshAble);

    /**
     * 滚动动画 监听
     *  @param scrollAnimation  回弹类型
     * @param fraction
     * @param headHeight
     * @param isPinContent
     */
    void animateBack(ScrollAnimation scrollAnimation, float fraction, float headHeight, ZRefreshLayout.HeadPin isPinContent);

    /**
     * 拦截滚动
     * 想让其滚动可使用AUtils.smoothScrollTo_NotIntercept(iScroll,0);
     * 参考：demo里的 CircleRefresh
     *
     * @param scrollAnimation
     * @param iScroll
     * @return
     */
    boolean interceptAnimateBack(ScrollAnimation scrollAnimation, IScroll iScroll);

    /**
     * 刷新进行时
     *
     * @param headHeight
     * @param isAutoRefresh
     */
    void onRefreshing(float headHeight, boolean isAutoRefresh);

    /**
     * 头部Rest前一刻调用此 用来重置状态
     */
    void onComplete();

    /**
     * 全局更换头的配置
     * 主要是为了复制头部对象，复制想要复制的属性
     * 注意:返回null时候，默认就是新浪头了
     */
    IHeaderView clone_();

}
