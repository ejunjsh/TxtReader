package com.sky.txtReader.widget;


/**
 * Created by shaojunjie on 2014/10/17.
 */
public interface OnScrollChangedListener {
    /**
     * 监听滚动变化
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    public void onScrollChanged(int l, int t, int oldl, int oldt);

    /**
     * 监听滚动到顶部
     */
    public void onScrollTop();

    /**
     * 监听滚动到底部
     */
    public void onScrollBottom();

}
