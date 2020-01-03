package zone.com.zrefreshlayout.resistance;

import zone.com.zrefreshlayout.IResistance;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class DampingHalfToHead implements IResistance {

    @Override
    public IResistance clone_() {
        DampingHalfToHead clone_ = new DampingHalfToHead();
        return clone_;
    }

    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset / 2 <= headerHeight ? offset / 2 : headerHeight;
    }
}
