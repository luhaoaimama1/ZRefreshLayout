package zone.com.zrefreshlayout.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class ScreenUtils {


    public static int[] getScreenPix(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int[] screen = new int[2];
        screen[0] = dm.widthPixels;
        screen[1] = dm.heightPixels;
        return screen;
    }

    public static int[] getScreenPixByResources(Context context) {
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        int[] screen = new int[2];
        screen[0] = dm2.widthPixels;
        screen[1] = dm2.heightPixels;
        return screen;
    }

    /**
     * 打印 显示信息
     */
    public static DisplayMetrics printDisplayInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append("_______  显示信息:  ");
        sb.append("\ndensity         :").append(dm.density);
        sb.append("\ndensityDpi      :").append(dm.densityDpi);
        sb.append("\nheightPixels    :").append(dm.heightPixels);
        sb.append("\nwidthPixels     :").append(dm.widthPixels);
        sb.append("\nscaledDensity   :").append(dm.scaledDensity);
        sb.append("\nxdpi            :").append(dm.xdpi);
        sb.append("\nydpi            :").append(dm.ydpi);
        Log.i("ScreenUtils", sb.toString());
        return dm;
    }
}
