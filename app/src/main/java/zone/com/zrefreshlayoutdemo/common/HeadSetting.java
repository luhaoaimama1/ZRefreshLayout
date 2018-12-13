package zone.com.zrefreshlayoutdemo.common;

import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class HeadSetting {
    public static final int METERIAL = 0;
    public static final int SINA = 1;
    public static final int WAVE = 2;

    private int headmode;
    private ZRefreshLayout.HeadPin headPin = ZRefreshLayout.HeadPin.NOT_PIN;

    public int getHeadmode() {
        return headmode;
    }

    public void setHeadmode(int headmode) {
        this.headmode = headmode;
    }

    public ZRefreshLayout.HeadPin headPin() {
        return headPin;
    }

    public void setPin(ZRefreshLayout.HeadPin pin) {
        this.headPin = pin;
    }
}
