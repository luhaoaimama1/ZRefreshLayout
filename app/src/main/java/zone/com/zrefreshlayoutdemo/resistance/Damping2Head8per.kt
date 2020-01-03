package zone.com.zrefreshlayoutdemo.resistance

import zone.com.zrefreshlayout.IResistance

/**
 * Created by fuzhipeng on 2017/1/10.
 */

class Damping2Head8per : IResistance {

    override fun clone_(): IResistance {
        return Damping2Head8per()
    }

    override fun getOffSetYMapValue(headerHeight: Int, offset: Int): Int {
        return if (offset / 2 <= headerHeight * 0.8) offset / 2 else (headerHeight * 0.8).toInt()
    }

}
