package zone.com.zrefreshlayoutdemo;
import android.app.Application;
import android.graphics.Color;

import com.zone.lib.Configuration;

import zone.com.zrefreshlayout.Config;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayout.footer.MeterialFooter;
import zone.com.zrefreshlayout.header.MeterialHead;
import zone.com.zrefreshlayout.header.SinaRefreshHeader;
import zone.com.zrefreshlayout.resistance.DampingHalf;
import zone.com.zrefreshlayoutdemo.common.Constant;
import zone.com.zrefreshlayoutdemo.common.HeadSetting;
import zone.com.zrefreshlayoutdemo.header.WaveHead;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class Apps extends Application {

    public static final int[] COLORS_RED_GREEN_YELLOW = new int[]{
            Color.parseColor("#ffF44336"),
            Color.parseColor("#ff4CAF50"),
            Color.parseColor("#ffFFEB3B")
    };
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Build.init(this).perform();
        setGlobalHead(SP1.INSTANCE.get(Constant.REFRESH_MODE,HeadSetting.class));
    }

    public static void setDefaultHeader() {
        Config.build()
                .setHeader(new MeterialHead(COLORS_RED_GREEN_YELLOW))
                .setHeadPin(ZRefreshLayout.HeadPin.NOT_PIN)
                .setFooter(new MeterialFooter())
                .setResistance(new DampingHalf())
//                .setHeader(new  CircleRefresh())
//                .setResistance(new Damping2Head8per())
                .writeLog(true)
                .perform();
    }


    public static void setGlobalHead(HeadSetting mHeadSetting) {
        Config temp = Config.build();
        if(mHeadSetting==null){
            setDefaultHeader();
            return;
        }
        switch (mHeadSetting.getHeadmode()) {
            case HeadSetting.METERIAL:
                temp.setHeader(new MeterialHead(COLORS_RED_GREEN_YELLOW));
                break;
            case HeadSetting.SINA:
                temp.setHeader(new SinaRefreshHeader());
                break;
            case HeadSetting.WAVE:
                WaveHead waveHead = new WaveHead();
                waveHead.setResourceId(R.drawable.wave);
                temp.setHeader(waveHead);
                break;
        }
        temp.setHeadPin(mHeadSetting.headPin())
                .setFooter(new MeterialFooter())
                .setResistance(new DampingHalf())
                .writeLog(true)
                .perform();
    }

}
