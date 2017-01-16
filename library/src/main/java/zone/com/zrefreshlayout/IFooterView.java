package zone.com.zrefreshlayout;

import android.content.Context;
import android.view.View;


/**
 * Created by fuzhipeng on 2017/1/16.
 */
public interface IFooterView {
    IFooterView clone_();
    View getView(Context context);
    void onStart(float footerHeight);
    void onComplete();
}
