# ZRefreshLayout

一个可以`全局`随意`配置头部`的`下拉刷新`与`上拉加载`库；

### 特点与功能实现
- [x] 支持添加下拉刷新与上拉加载
- [x] 支持所有的View:ImageView,FrameLayout,ListView, GridView, ScrollView, WebView...
- [x] 全局配置(头部配置都在这里,所以没有xml属性~),与独立更改
- [x] 自动刷新
- [x] 头部是否固定
- [x] 刷新出发位置
- [x] 下拉位置映射，既resistance阻力
- [x] 因为是继承FrameLayout,可以添加多个child。(我没有让控制必须一个,感觉更灵活吧)

### JicPack
Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
Step 2. Add the dependency

> compile 'com.github.luhaoaimama1:ZRefreshLayout:[Latest release](https://github.com/luhaoaimama1/ZRefreshLayout/releases)'
    
## Preview

#### 固定头
![](https://ww4.sinaimg.cn/large/006tKfTcly1fbtqosupizg307i0dcb29.gif)

#### Meterial Header与Footer
![](https://ww4.sinaimg.cn/large/006tKfTcly1fbtqsrspgjg307i0dc4bx.gif)

#### SinaRefreshHeader与LoadFooter
![](https://ww3.sinaimg.cn/large/006tKfTcly1fbtqt3ghb1g307i0dch1l.gif)

#### WaveHead
![](https://ww3.sinaimg.cn/large/006tKfTcly1fbtqtb17leg307i0dcn8w.gif)

#### CircleRefresh Head
>[引用tuesda的CircleView 主要是为了测试延迟功能](https://github.com/tuesda/CircleRefreshLayout)

![](https://ww3.sinaimg.cn/large/006tKfTcly1fbtqq5dl7vg307i0dc48d.gif)

## Usage

#### 全局配置

```
      Config.build()
            .setHeader(new MeterialHead())
            .setFooter(new MeterialFooter())
            .setResistance(new Damping())
            .writeLog(true)
            .perform();
```
#### 独立更改

```
    refresh.setIHeaderView(new SinaRefreshHeader());
```

#### 头部固定否

```
    refresh.setPinContent(true);
```

#### 自动刷新

```
    refresh.autoRefresh(haveAnimate)
```

### 下拉监听
> 刷新完成记得使用:**zRefreshLayout.refreshComplete();**

```
     refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                listView.smoothScrollToPosition(0);
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add("刷新完毕:" + i++);
                        adapter2.notifyDataSetChanged();
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

```

#### 上拉监听
> 如果没有设置上啦监听则不会有上拉加载功能

> 加载完成记得使用:**zRefreshLayout.loadMoreComplete();**

```
      refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add("加载完毕:" + i++);
                        zRefreshLayout.loadMoreComplete();
                    }
                }, 2000);

            }

            @Override
            public void complete(ZRefreshLayout zRefreshLayout) {

            }
        });
```

>头部自定义与底部自定义和更多高级功能,请看[**Wiki 文档**](https://github.com/luhaoaimama1/ZRefreshLayout/wiki);

# Reference&Thanks：

https://github.com/lcodecorex/TwinklingRefreshLayout

https://github.com/tuesda/CircleRefreshLayout

https://github.com/android-cjj/Android-MaterialRefreshLayout

https://github.com/desmond1121/Android-Ptr-Comparison


