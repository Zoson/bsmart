package com.koalitech.service.pay;

/**
 * Created by Zoson on 15/11/15.
 */
public interface IPayService {
    public final static int WEIXIN = 0;
    public final static int ALI = 1;
    public void tryPay(float price, String subject, String body, IPayStatusCallback payStatusCallback);
}
