package zone.com.zrefreshlayout;

import android.view.View;


/**
 * Created by fuzhipeng on 2017/1/16.
 */
public interface IFooterView {
    /**
     * 全局更换头的配置
     * 主要是为了复制头部对象，复制想要复制的属性
     * 注意:返回null时候，默认就是LoadFooter了
     */
    IFooterView clone_();
    /**
     * 获取footer布局
     * @param zRefreshLayout
     * @return
     */
    View getView(ZRefreshLayout zRefreshLayout);

    /**
     * loadMore中
     * @param footerHeight
     */
    void onStart(ZRefreshLayout zRefreshLayout, float footerHeight);
    /**
     * loadMore 结束
     */
    void onComplete(ZRefreshLayout zRefreshLayout);
}
