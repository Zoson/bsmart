package com.koalitech.bsmart.domain.remote;

/**
 * Created by Zoson on 2016/10/5.
 */
public interface HttpListener {
    public void succToRequire(String msg,String data);
    public void failToRequire(String msg, String data);
    public void netWorkError(String msg, String data);
}
