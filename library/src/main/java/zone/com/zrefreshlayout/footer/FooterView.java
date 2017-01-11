package zone.com.zrefreshlayout.footer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import zone.com.zrefreshlayout.IFooterView;
import zone.com.zrefreshlayout.R;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class FooterView implements IFooterView {
    private View rootView;
    private ImageView loadingView;

    @Override
    public View getView(Context context) {
        rootView= View.inflate(context, R.layout.footer, null);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        return rootView;
    }

    @Override
    public void onStart(float footerHeight) {
        loadingView.setVisibility(View.VISIBLE);
        ((AnimationDrawable)loadingView.getDrawable()).start();
    }

    @Override
    public void onComplete() {
        ((AnimationDrawable)loadingView.getDrawable()).stop();
        loadingView.setVisibility(View.INVISIBLE);
    }
    @Override
    public IFooterView clone_() {
        FooterView clone =new FooterView();
        return clone;
    }
}
