package com.app.riststock.models;

/**
 * Created by ameer on 6/6/2017.
 */

public class MessageEvent {

    public static final String TYPE_PAGER = "pager";
    public static final String TYPE_DATA = "data";
    public static final String TYPE_LOCATION = "location";
    public static final String TYPE_FCM_TOKEN = "fcm_token";
    public static final String TYPE_POSITION = "position";
    public static final String TYPE_REFRESH = "refresh";
    public static final String TYPE_COUNTER = "counter";
    public static final String TYPE_FRAGMENT = "fragment";
    public static final String TYPE_CHANGE_LANGUAGE = "change_language";

    //    public int PagerPosition;
    public Object data;
    public String type;

    public MessageEvent(String type, Object msgData) {
        data = msgData;
        this.type = type;
    }

    public MessageEvent(String type) {
        this.type = type;
    }

    public MessageEvent() {
    }

}
