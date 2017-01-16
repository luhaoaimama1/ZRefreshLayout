package zone.com.zrefreshlayout;

import zone.com.zrefreshlayout.utils.LogUtils;

/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class Config {

    IHeaderView headerView;
    IFooterView footerView;
    IResistance resistance;
    boolean isPinContent;
    boolean isDebug;

    private Config() {
    }

    public static Config build() {
        return new Config();
    }


    public Config setHeader(IHeaderView headerView) {
        this.headerView = headerView;
        return this;
    }


    public Config setFooter(IFooterView footerView) {
        this.footerView = footerView;
        return this;
    }

    public Config setResistance(IResistance resistance) {
        this.resistance = resistance;
        return this;
    }

    public Config setPinContent(boolean pinContent) {
        this.isPinContent = pinContent;
        return this;
    }

    public Config writeLog(boolean writeLog) {
        this.isDebug = writeLog;
        return this;
    }


    public void perform() {
        ZRefreshLayout.config = this;
        LogUtils.isDebug = isDebug;
    }

}
