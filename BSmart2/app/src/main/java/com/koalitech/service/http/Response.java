package com.koalitech.service.http;

/**
 * Created by Zoson on 2016/10/5.
 */
public class Response {
    public int getState() {
        return state;
    }

    public byte[] getContent() {
        return content;
    }

    int state;
    byte[] content;
}
