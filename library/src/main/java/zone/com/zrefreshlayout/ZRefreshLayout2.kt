package zone.com.zrefreshlayout

import android.content.Context
import android.util.AttributeSet

/**
 *[2018/12/12] by Zone
 */
class ZRefreshLayout2 :ZRefreshLayout{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    inner class Scroll_PinNot : IScroll() {

        override fun scrollTo(fy: Int, isTriggerHeaderOnPullingDown: Boolean) {
            if (mIHeaderView != null && isTriggerHeaderOnPullingDown)
                mIHeaderView.onPullingDown(1f * Math.abs(fy) / refreshAbleHeight, refreshAbleHeight.toFloat())
            scrollTo(0, -fy)
        }

        override fun getScrollY(): Int {
            return -getScrollY()
        }
    }
}