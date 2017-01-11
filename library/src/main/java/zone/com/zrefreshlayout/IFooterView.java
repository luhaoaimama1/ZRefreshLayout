package zone.com.zrefreshlayout;

import android.content.Context;
import android.view.View;

/**
 * Created by lcodecore on 2016/10/1.
 */

public interface IFooterView {
    IFooterView clone_();
    View getView(Context context);
    void onStart(float footerHeight);
    void onComplete();
}
