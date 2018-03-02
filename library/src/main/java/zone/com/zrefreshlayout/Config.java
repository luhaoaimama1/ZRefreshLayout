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
    ZRefreshLayout.PullListener mPullListener;
    ZRefreshLayout.LoadMoreStateRestListener mLoadMoreStateRestListener;
    ZRefreshLayout.PullStateRestListener mPullStateRestListener;
    ZRefreshLayout.LoadMoreListener mLoadMoreListener;
    int delay_millis_auto_complete=10000;


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

    public Config setLoadMoreListener(ZRefreshLayout.LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
        return this;
    }
    public Config setLoadMoreListener(ZRefreshLayout.LoadMoreListener mLoadMoreListener, ZRefreshLayout.LoadMoreStateRestListener mLoadMoreStateRestListener) {
        this.mLoadMoreListener=mLoadMoreListener;
        this.mLoadMoreStateRestListener=mLoadMoreStateRestListener;
        return this;
    }
    public Config setPullListener(ZRefreshLayout.PullListener mPullListener) {
        this.mPullListener = mPullListener;
        return this;
    }

    public Config setPullListener(ZRefreshLayout.PullListener mPullListener, ZRefreshLayout.PullStateRestListener mPullStateRestListener) {
        this.mPullListener = mPullListener;
        this.mPullStateRestListener = mPullStateRestListener;
        return this;
    }

    public Config writeLog(boolean writeLog) {
        this.isDebug = writeLog;
        return this;
    }
    public Config setDelayAutoCompleteTime(int delayAutoComplete) {
        this.delay_millis_auto_complete = delayAutoComplete;
        return this;
    }


    public void perform() {
        ZRefreshLayout.config = this;
        LogUtils.isDebug = isDebug;
    }

}
