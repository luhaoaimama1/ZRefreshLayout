package zone.com.zrefreshlayout

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import zone.com.zrefreshlayout.header.NullHeader
import zone.com.zrefreshlayout.utils.LogUtils.log

/**
 *[2018/12/12] by Zone
 */
class FlexibilityLayout : ZRefreshLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun initHeaderView() {
//        super.initHeaderView()
        this.mIHeaderView = NullHeader()
        headerView = mIHeaderView.initView(this)
    }

    override fun setIHeaderView(mIHeaderView: IHeaderView) {
        this.mIHeaderView = NullHeader()
        super.setIHeaderView(mIHeaderView)
    }


    override fun autoRefresh(haveAnimate: Boolean) {
        throw IllegalStateException("不支持刷新")
    }

    override fun realMove(dy2: Float) {
        var dy=dy2
        direction = if (dy > 0) PULL_DOWN
        else LOAD_UP

        //dy 需要映射不然 真实的移动 和 MotionEvent移动对不上了 也没法和getRefreshAbleHeight()[真实的]比较了
        if (mIResistance != null)
            dy = mIResistance.getOffSetYMapValue(refreshAbleHeight, dy.toInt()).toFloat()

        if (state == REST && direction == PULL_DOWN) state = PULL

        if (state == PULL) {
            //防止别的状态 可以滚动
            mIScroll.scrollTo_(dy.toInt())
        }
    }

    override fun realCancel() {
        if (state == PULL) {
            //回弹
            mScrollAnimation = ScrollAnimation.DisRefreshAble_BackAnimation
            mIScroll.smoothScrollTo_(0)
            log("回弹！")
        }
    }
}