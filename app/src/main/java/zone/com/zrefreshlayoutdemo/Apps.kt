package zone.com.zrefreshlayoutdemo

import android.app.Application
import android.graphics.Color
import com.zone.lib.Configure
import zone.com.zrefreshlayout.Config
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayout.footer.MeterialFooter
import zone.com.zrefreshlayout.header.MeterialHead
import zone.com.zrefreshlayout.header.SinaRefreshHeader
import zone.com.zrefreshlayout.resistance.DampingHalf
import zone.com.zrefreshlayoutdemo.common.Constant
import zone.com.zrefreshlayoutdemo.common.HeadSetting
import zone.com.zrefreshlayoutdemo.header.WaveHead

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
class Apps : Application() {
    override fun onCreate() {
        super.onCreate()
        Configure.init(this)
        setGlobalHead(SP1.get(Constant.REFRESH_MODE, HeadSetting::class.java))
    }

    companion object {

        val COLORS_RED_GREEN_YELLOW = intArrayOf(Color.parseColor("#ffF44336"), Color.parseColor("#ff4CAF50"), Color.parseColor("#ffFFEB3B"))

        fun setDefaultHeader() {
            Config.build()
                    .setHeader(MeterialHead(COLORS_RED_GREEN_YELLOW))
                    .setHeadPin(ZRefreshLayout.HeadPin.NOT_PIN)
                    .setFooter(MeterialFooter())
                    .setResistance(DampingHalf())
                    //                .setHeader(new  CircleRefresh())
                    //                .setResistance(new Damping2Head8per())
                    .writeLog(true)
                    .perform()
        }


        fun setGlobalHead(mHeadSetting: HeadSetting?) {
            val temp = Config.build()
            if (mHeadSetting == null) {
                setDefaultHeader()
                return
            }
            when (mHeadSetting.headmode) {
                HeadSetting.METERIAL -> temp.setHeader(MeterialHead(COLORS_RED_GREEN_YELLOW))
                HeadSetting.SINA -> temp.setHeader(SinaRefreshHeader())
                HeadSetting.WAVE -> {
                    val waveHead = WaveHead()
                    waveHead.resourceId = R.drawable.wave
                    temp.setHeader(waveHead)
                }
            }
            temp.setHeadPin(mHeadSetting.headPin())
                    .setFooter(MeterialFooter())
                    .setResistance(DampingHalf())
                    .writeLog(true)
                    .perform()
        }
    }

}
