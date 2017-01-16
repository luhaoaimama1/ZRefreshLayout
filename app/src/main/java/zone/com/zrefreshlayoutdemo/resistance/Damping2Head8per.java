package zone.com.zrefreshlayoutdemo.resistance;

import zone.com.zrefreshlayout.IResistance;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class Damping2Head8per implements IResistance {

    @Override
    public IResistance clone_() {
        Damping2Head8per clone_ = new Damping2Head8per();
        return clone_;
    }

    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset / 2 <= headerHeight*0.8 ? offset / 2 : (int) (headerHeight * 0.8);
    }

}
