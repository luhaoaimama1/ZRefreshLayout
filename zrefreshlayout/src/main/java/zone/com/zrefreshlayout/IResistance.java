package zone.com.zrefreshlayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public interface IResistance {
    /**
     * 全局更换头的配置
     * 主要是为了复制头部对象，复制想要复制的属性
     * 注意:返回null时候，默认就是没有映射了
     */
    IResistance clone_();

    /**
     *
     * @param headerHeight
     * @param offset  是>0的值
     * @return
     */
    int getOffSetYMapValue(int headerHeight, int offset);
}
