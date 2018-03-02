package zone.com.zrefreshlayoutdemo.common;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class HeadSetting {
    public static final int METERIAL = 0;
    public static final int SINA = 1;
    public static final int WAVE = 2;

    private int headmode;
    private boolean pin;

    public int getHeadmode() {
        return headmode;
    }

    public void setHeadmode(int headmode) {
        this.headmode = headmode;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }
}
