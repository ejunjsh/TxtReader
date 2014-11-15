package com.sky.txtReader.utils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shaojunjie on 2014/10/30.
 */
public class BookBean implements Serializable{
    private String path;
    private long order;

    private int markStart;

    private int markEnd;
    private long time;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMarkStart() {
        return markStart;
    }

    public void setMarkStart(int markStart) {
        this.markStart = markStart;
    }

    public int getMarkEnd() {
        return markEnd;
    }

    public void setMarkEnd(int markEnd) {
        this.markEnd = markEnd;
    }
}
