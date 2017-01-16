package com.koalitech.service.http;

/**
 * Created by Zoson on 16/10/4.
 */
public interface IHttpRequest {
    public void sendPost(Request request);
    public void sendGet(Request request);
}
