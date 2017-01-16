package zone.com.zrefreshlayout;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public interface IResistance {
    IResistance clone_();

    /**
     *
     * @param headerHeight
     * @param offset  是>0的值
     * @return
     */
    int getOffSetYMapValue(int headerHeight,int offset);
}
