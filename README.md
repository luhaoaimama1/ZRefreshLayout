
[中文版文档](README-cn.md)

# ZRefreshLayout

A global random configuration head , drop-down refresh and  loadMore library;

### Feature
- [x] support refresh and  loadMore
- [x] support All the View:ImageView,FrameLayout,ListView, GridView, ScrollView, WebView...
- [x] support Global configuration (HeaderView's configuration is here,so not have xml's property),and Independent changes
- [x] support auto refresh
- [x] support header fixed
- [x] support refresh's Trigger position
- [x] support drop-down's position map(as IResistance)
- [x] support add many childs。because extends FrameLayout(not must be one,because more changeful)

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
    

## Usage

#### Global configuration

```
      Config.build()
            .setHeader(new MeterialHead())
            .setFooter(new MeterialFooter())
            .setResistance(new Damping())
            .writeLog(true)
            .perform();
```
#### Independent changes

```
    refresh.setIHeaderView(new SinaRefreshHeader());
```

#### header fixed

```
    refresh.setPinContent(true);
```

#### auto refresh

```
    refresh.autoRefresh(haveAnimate)
```

### refresh's listener
> refresh complete ,Remember to use:**zRefreshLayout.refreshComplete();**

```
     refresh.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                listView.smoothScrollToPosition(0);
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add("refresh:" + i++);
                        adapter2.notifyDataSetChanged();
                        zRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }

```

#### loadMore's Listener
> if not setLoadMoreListener,not have loadMore feature;

> loadMore complete ,Remember to use:**zRefreshLayout.loadMoreComplete();**

```
      refresh.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.add("loadMore:" + i++);
                        zRefreshLayout.loadMoreComplete();
                    }
                }, 2000);

            }

            @Override
            public void complete(ZRefreshLayout zRefreshLayout) {

            }
        });
```

> HeadView and FooterView 's custom and More advanced features,please see [**Wiki Document**](https://github.com/luhaoaimama1/ZRefreshLayout/wiki);;

# Reference&Thanks：

https://github.com/lcodecorex/TwinklingRefreshLayout

https://github.com/tuesda/CircleRefreshLayout

