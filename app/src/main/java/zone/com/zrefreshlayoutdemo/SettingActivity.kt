package zone.com.zrefreshlayoutdemo

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.a_setting.*
import zone.com.zrefreshlayout.ZRefreshLayout
import zone.com.zrefreshlayoutdemo.common.Constant
import zone.com.zrefreshlayoutdemo.common.HeadSetting

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

class SettingActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private var mHeadSetting: HeadSetting? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_setting)
        rb_pin!!.setOnCheckedChangeListener(this)
        rb_head!!.setOnCheckedChangeListener(this)
        mHeadSetting = SP1.get(Constant.REFRESH_MODE, HeadSetting::class.java)
        if (mHeadSetting == null) {
            mHeadSetting = HeadSetting()
            rb_head_meterial!!.performClick()
            rb_pin_false!!.performClick()
        } else {
            when (mHeadSetting!!.headmode) {
                HeadSetting.METERIAL -> rb_head_meterial!!.performClick()
                HeadSetting.SINA -> rb_head_sina!!.performClick()
                HeadSetting.WAVE -> rb_head_wave!!.performClick()
            }
            if (mHeadSetting!!.headPin() == ZRefreshLayout.HeadPin.PIN)
                rb_pin_true!!.performClick()
            else
                rb_pin_false!!.performClick()
        }

    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.tv_Clear -> {
                    SP1.remove(Constant.REFRESH_MODE)
                    Apps.setDefaultHeader()
                }
                R.id.Save -> {
                    SP1.put<HeadSetting>(Constant.REFRESH_MODE, mHeadSetting)
                    Apps.setGlobalHead(mHeadSetting)
                }
            }
            finish()
        }
    }

    override fun onCheckedChanged(group: RadioGroup, @IdRes checkedId: Int) {
        if (group === rb_pin)
            onCheckedChanged_Pin(checkedId)
        if (group === rb_head)
            onCheckedChanged_Head(checkedId)
    }

    fun onCheckedChanged_Pin(@IdRes checkedId: Int) {
        if (checkedId == R.id.rb_pin_true)
            mHeadSetting!!.setPin(ZRefreshLayout.HeadPin.PIN)
        else
            mHeadSetting!!.setPin(ZRefreshLayout.HeadPin.NOT_PIN)
    }

    fun onCheckedChanged_Head(@IdRes checkedId: Int) {
        when (checkedId) {
            R.id.rb_head_meterial -> mHeadSetting!!.headmode = HeadSetting.METERIAL
            R.id.rb_head_sina -> mHeadSetting!!.headmode = HeadSetting.SINA
            R.id.rb_head_wave -> mHeadSetting!!.headmode = HeadSetting.WAVE
        }
    }
}
