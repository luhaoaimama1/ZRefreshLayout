package zone.com.zrefreshlayout.utils;

import android.util.Log;

/**
 * Created by fuzhipeng on 2017/1/16.
 */

public class LogUtils {

    public static final String Z_REFRESH_LAYOUT = "ZRefreshLayout";
    public static boolean isDebug = true;

    public static void log(String str) {
        if (isDebug)
            Log.i(Z_REFRESH_LAYOUT, str);
    }
    public static void logE(String str) {
        if (isDebug)
            Log.e(Z_REFRESH_LAYOUT, str);
    }
}
