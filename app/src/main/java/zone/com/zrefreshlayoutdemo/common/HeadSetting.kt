package zone.com.zrefreshlayoutdemo.common

import zone.com.zrefreshlayout.ZRefreshLayout

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

class HeadSetting {

    var headmode: Int = 0
    private var headPin: ZRefreshLayout.HeadPin = ZRefreshLayout.HeadPin.NOT_PIN

    fun headPin(): ZRefreshLayout.HeadPin {
        return headPin
    }

    fun setPin(pin: ZRefreshLayout.HeadPin) {
        this.headPin = pin
    }

    companion object {
        val METERIAL = 0
        val SINA = 1
        val WAVE = 2
    }
}
