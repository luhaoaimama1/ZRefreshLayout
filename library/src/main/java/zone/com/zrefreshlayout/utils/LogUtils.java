package zone.com.zrefreshlayout.utils;

import android.util.Log;

/**
 * Created by fuzhipeng on 2017/1/16.
 */

public class LogUtils {

    public static boolean isDebug = true;

    public static void log(String str) {
        if (isDebug)
            Log.i("ZRefreshLayout", str);
    }
}
