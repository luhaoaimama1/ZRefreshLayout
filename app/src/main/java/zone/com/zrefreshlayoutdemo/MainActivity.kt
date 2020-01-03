package zone.com.zrefreshlayoutdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import zone.com.zrefreshlayoutdemo.common.Constant
import zone.com.zrefreshlayoutdemo.common.HeadSetting

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

    }

    override fun onClick(view: View?) {
        view?.let {
            if (it.id == R.id.globalView)
                Apps.setGlobalHead(SP1.get(Constant.REFRESH_MODE, HeadSetting::class.java))
            else
                Apps.setDefaultHeader()

            when (it.id) {
                R.id.globalView -> startActivity(Intent(this, GlobalViewActivity::class.java))
                R.id.tv_mode -> startActivity(Intent(this, SettingActivity::class.java))
                R.id.textView -> startActivity(Intent(this, TextViewActivity::class.java))
                R.id.listView -> startActivity(Intent(this, ListViewActivity::class.java))
                R.id.recyclerView -> startActivity(Intent(this, RecyclerViewActivity::class.java))
                R.id.gridView -> startActivity(Intent(this, GridViewActivity::class.java))
                R.id.scrollerView -> startActivity(Intent(this, ScrollerViewActivity::class.java))
                R.id.webView -> startActivity(Intent(this, WebViewActivity::class.java))
                R.id.autoRefresh -> startActivity(Intent(this, AutoRefreshActivity::class.java))
                R.id.pinContent -> startActivity(Intent(this, PinContentActivity::class.java))
                R.id.uniqueFeature -> startActivity(Intent(this, UniqueFeatureActivity::class.java))
                R.id.refreshAblePosition -> startActivity(Intent(this, RefreshPositionActivity::class.java))
                R.id.resistance -> startActivity(Intent(this, ResistanceActivity::class.java))
                R.id.waveHeader -> startActivity(Intent(this, WaveHeaderActivity::class.java))
                R.id.circleHeader -> startActivity(Intent(this, CircleHeaderActivity::class.java))
                R.id.sinaRefresh -> startActivity(Intent(this, SinaHeaderActivity::class.java))
                R.id.meterialHeader -> startActivity(Intent(this, MeterialHeaderActivity::class.java))
                R.id.cirlcleActivity -> startActivity(Intent(this, CirlcleActivity::class.java))
                R.id.flexibilityListViewActivity -> startActivity(Intent(this, FlexibilityKtActivity::class.java))
                R.id.nestScroller -> startActivity(Intent(this, ScrollerNesttActivity::class.java))
                else -> {
                }
            }
        }

    }

}
