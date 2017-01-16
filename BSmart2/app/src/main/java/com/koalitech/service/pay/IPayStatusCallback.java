package com.koalitech.service.pay;

/**
 * Created by Zoson on 15/11/15.
 */
public interface IPayStatusCallback {
    public static final int SUCCESSFULLY = 0x0;
    public static final int UNSUCCESSFULLY = 0x1;
    public static final int HANDING = 0x2;
    public void payState(int state, String msg);
    public void checkAccount(boolean check);
}
