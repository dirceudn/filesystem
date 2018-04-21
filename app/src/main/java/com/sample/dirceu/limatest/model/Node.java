package com.sample.dirceu.limatest.model;

import java.io.Serializable;

public class Node implements Serializable {

    private int id;
    private String name;
    private String mimetype;
    private int size;
    private long notification_time;
    private String path;
    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(long notification_time) {
        this.notification_time = notification_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
