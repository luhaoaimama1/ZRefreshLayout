package zone.com.zrefreshlayoutdemo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zone.lib.utils.data.file2io2data.SharedUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.zrefreshlayoutdemo.common.Constant;
import zone.com.zrefreshlayoutdemo.common.HeadSetting;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.rb_head)
    RadioGroup rbHead;
    @Bind(R.id.rb_pin)
    RadioGroup rbPin;
    @Bind(R.id.rb_head_meterial)
    RadioButton rbHeadMeterial;
    @Bind(R.id.rb_pin_true)
    RadioButton rbPinTrue;
    @Bind(R.id.rb_head_sina)
    RadioButton rbHeadSina;
    @Bind(R.id.rb_head_wave)
    RadioButton rbHeadWave;
    @Bind(R.id.rb_pin_false)
    RadioButton rbPinFalse;
    private HeadSetting mHeadSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_setting);
        ButterKnife.bind(this);
        rbPin.setOnCheckedChangeListener(this);
        rbHead.setOnCheckedChangeListener(this);
        mHeadSetting = SharedUtils.get(Constant.REFRESH_MODE, HeadSetting.class);
        if (mHeadSetting == null) {
            mHeadSetting = new HeadSetting();
            rbHeadMeterial.performClick();
            rbPinTrue.performClick();
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
            if (mHeadSetting.isPin())
                rbPinTrue.performClick();
            else
                rbPinFalse.performClick();
        }

    }

    @OnClick({R.id.tv_Clear, R.id.Save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_Clear:
                SharedUtils.remove(Constant.REFRESH_MODE);
                Apps.setDefaultHeader();
                break;
            case R.id.Save:
                SharedUtils.put(Constant.REFRESH_MODE, mHeadSetting);
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
            mHeadSetting.setPin(true);
        else
            mHeadSetting.setPin(false);
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
