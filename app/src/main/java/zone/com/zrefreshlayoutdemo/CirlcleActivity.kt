package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import kotlinx.android.synthetic.main.a_circle.*
import zone.com.zanimate.value.ValueAnimatorProxy
import zone.com.zrefreshlayout.utils.ScreenUtils
import zone.com.zrefreshlayout.v4.MeterialCircle

/**
 * [2017] by Zone
 */

class CirlcleActivity : AppCompatActivity(), View.OnClickListener {

    var mMeterialCircle: MeterialCircle? = null

    private val valueAnimator = ValueAnimatorProxy.ofFloat(0F, 1f)
            .setDuration(1200)
            .setInterpolator(LinearInterpolator())
            .addUpdateListener { animation ->
                println("值?" + animation.animatedValue as Float)
                mMeterialCircle?.pullProgress(animation.animatedValue as Float)
            }
            .addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mMeterialCircle?.start()
                }
            })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_circle)
        root.post {
            val screenPixs = ScreenUtils.getScreenPix(this@CirlcleActivity)
            mMeterialCircle = MeterialCircle(root, (screenPixs[1] * 0.2).toInt())
            root.addView(mMeterialCircle!!.view)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.bt_start -> {
                if (valueAnimator.isRunning)
                    valueAnimator.cancel()
                mMeterialCircle?.reset()
                valueAnimator.start()
            }
            R.id.bt_stop -> mMeterialCircle?.reset()
            R.id.bt_reStart -> mMeterialCircle?.startScaleDownAnimation { Log.e("CirlcleActivity", "over") }
            else -> {
            }
        }
    }
}
