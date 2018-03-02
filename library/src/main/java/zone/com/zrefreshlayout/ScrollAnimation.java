package zone.com.zrefreshlayout;

/**
 * Created by fuzhipeng on 2017/1/13.
 */

public enum ScrollAnimation {
    None,//未回弹 初始状态
    AutoRefresh_Animation,//自动刷新 滚动到头部高度
    DisRefreshAble_BackAnimation,//未达到刷新 直接回弹0
    RefreshAble_BackAnimation,//超过刷新刷新位置 回弹到可刷新位置
    Complete_BackAnimation;//完成刷新后回弹
}
