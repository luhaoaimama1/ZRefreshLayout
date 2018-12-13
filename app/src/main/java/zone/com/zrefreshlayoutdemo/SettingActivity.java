package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayout.ZRefreshLayout;
import zone.com.zrefreshlayoutdemo.common.Constant;
import zone.com.zrefreshlayoutdemo.common.HeadSetting;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rb_head)
    RadioGroup rbHead;
    @BindView(R.id.rb_pin)
    RadioGroup rbPin;
    @BindView(R.id.rb_head_meterial)
    RadioButton rbHeadMeterial;
    @BindView(R.id.rb_pin_true)
    RadioButton rbPinTrue;
    @BindView(R.id.rb_head_sina)
    RadioButton rbHeadSina;
    @BindView(R.id.rb_head_wave)
    RadioButton rbHeadWave;
    @BindView(R.id.rb_pin_false)
    RadioButton rbPinFalse;
    private HeadSetting mHeadSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setting);
        ButterKnife.bind(this);
        rbPin.setOnCheckedChangeListener(this);
        rbHead.setOnCheckedChangeListener(this);
        mHeadSetting = SP1.INSTANCE.get(Constant.REFRESH_MODE, HeadSetting.class);
        if (mHeadSetting == null) {
            mHeadSetting = new HeadSetting();
            rbHeadMeterial.performClick();
            rbPinFalse.performClick();
        } else {
            switch (mHeadSetting.getHeadmode()) {
                case HeadSetting.METERIAL:
                    rbHeadMeterial.performClick();
                    break;
                case HeadSetting.SINA:
                    rbHeadSina.performClick();
                    break;
                case HeadSetting.WAVE:
                    rbHeadWave.performClick();
                    break;
            }
            if (mHeadSetting.headPin() == ZRefreshLayout.HeadPin.PIN)
                rbPinTrue.performClick();
            else
                rbPinFalse.performClick();
        }

    }

    @OnClick({R.id.tv_Clear, R.id.Save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_Clear:
                SP1.INSTANCE.remove(Constant.REFRESH_MODE);
                Apps.setDefaultHeader();
                break;
            case R.id.Save:
                SP1.INSTANCE.put(Constant.REFRESH_MODE, mHeadSetting);
                Apps.setGlobalHead(mHeadSetting);
                break;
        }
        finish();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (group == rbPin)
            onCheckedChanged_Pin(checkedId);
        if (group == rbHead)
            onCheckedChanged_Head(checkedId);
    }

    public void onCheckedChanged_Pin(@IdRes int checkedId) {
        if (checkedId == R.id.rb_pin_true)
            mHeadSetting.setPin(ZRefreshLayout.HeadPin.PIN);
        else
            mHeadSetting.setPin(ZRefreshLayout.HeadPin.NOT_PIN);
    }

    public void onCheckedChanged_Head(@IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_head_meterial:
                mHeadSetting.setHeadmode(HeadSetting.METERIAL);
                break;
            case R.id.rb_head_sina:
                mHeadSetting.setHeadmode(HeadSetting.SINA);
                break;
            case R.id.rb_head_wave:
                mHeadSetting.setHeadmode(HeadSetting.WAVE);
                break;
        }
    }
}
