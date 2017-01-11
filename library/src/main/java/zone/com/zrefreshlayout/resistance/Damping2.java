package zone.com.zrefreshlayout.resistance;

import zone.com.zrefreshlayout.IResistance;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class Damping2  implements IResistance {

    @Override
    public IResistance clone_() {
        Damping2 clone_ = new Damping2();
        return clone_;
    }
    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset/2;
    }

}
