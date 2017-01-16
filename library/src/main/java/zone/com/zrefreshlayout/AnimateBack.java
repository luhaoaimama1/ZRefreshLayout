package zone.com.zrefreshlayout;

/**
 * Created by fuzhipeng on 2017/1/13.
 */

public enum AnimateBack {
    None,//未回弹 初始状态
    DisRefreshAble_Back,//未达到刷新 直接回弹0
    RefreshAble_Back,//回弹后可刷新
    Complete_Back;//完成刷新后回弹
}
