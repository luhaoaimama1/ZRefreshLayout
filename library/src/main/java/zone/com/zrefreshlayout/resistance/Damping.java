package zone.com.zrefreshlayout.resistance;

import zone.com.zrefreshlayout.IResistance;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class Damping implements IResistance {

    @Override
    public IResistance clone_() {
        Damping clone_ = new Damping();
        return clone_;
    }
    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset/2;
    }

}
