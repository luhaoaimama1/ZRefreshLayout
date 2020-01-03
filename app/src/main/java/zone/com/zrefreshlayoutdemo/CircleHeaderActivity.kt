package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.unique_feature.*
import zone.com.zrefreshlayoutdemo.header.CircleRefresh

/**
 * Created by fuzhipeng on 2017/1/11.
 */

class CircleHeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unique_feature)
        refresh.setIHeaderView(CircleRefresh())
        //        refresh.setIResistance(new Damping2Head8per());

        tv.text = "包含滚动拦截后,准备就绪后,在滚动到特定位置! 还有下拉位置映射，刷新位置更改"
        refresh.setPullListener({ zRefreshLayout -> refresh.postDelayed({ zRefreshLayout.refreshComplete() }, 2000) }, { iv.setImageResource(R.drawable.aaaaaaaaaaaab) })
    }
}
