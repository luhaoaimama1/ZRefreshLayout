package zone.com.zrefreshlayoutdemo.resistance;

import zone.com.zrefreshlayout.IResistance;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class Damping2Head implements IResistance {

    @Override
    public IResistance clone_() {
        Damping2Head clone_ = new Damping2Head();
        return clone_;
    }

    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset / 2 <= headerHeight ? offset / 2 : headerHeight;
    }

}
