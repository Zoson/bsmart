package com.koalitech.service.http;

/**
 * Created by Zoson on 16/10/4.
 */
public interface IFileTransport {
    public void downloadFile(Request request,IFtListener listener);
    public void uploadFile(Request request,IFtListener listener);
}
