package zone.com.zrefreshlayout.resistance;

import zone.com.zrefreshlayout.IResistance;
import zone.com.zrefreshlayout.resistance.utils.DampingUtils;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class Damping implements IResistance {

    private  float maxLengthRadio;
    private  float dampingRadio;

    public Damping() {
        this(4F, 0.2F);
    }

    /**
     * @param maxLengthRadio 可以达到的最大距离 是对头部的比率
     * @param dampingRadio   [0-1]  更改曲线的~ 越大 阻力越大的感觉。
     */
    public Damping(float maxLengthRadio, float dampingRadio) {
        this.maxLengthRadio = maxLengthRadio;
        this.dampingRadio = dampingRadio;
    }

    @Override
    public IResistance clone_() {
        Damping clone_ = new Damping();
        clone_.maxLengthRadio=maxLengthRadio;
        clone_.dampingRadio=dampingRadio;
        return clone_;
    }

    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return (int) DampingUtils.damping(offset, maxLengthRadio * headerHeight, dampingRadio);
    }

    public float getMaxLengthRadio() {
        return maxLengthRadio;
    }

    public float getDampingRadio() {
        return dampingRadio;
    }

    public void setMaxLengthRadio(float maxLengthRadio) {
        this.maxLengthRadio = maxLengthRadio;
    }

    public void setDampingRadio(float dampingRadio) {
        this.dampingRadio = dampingRadio;
    }
}
