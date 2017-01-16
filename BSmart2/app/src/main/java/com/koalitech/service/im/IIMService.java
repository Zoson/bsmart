package com.koalitech.service.im;

/**
 * Created by Zoson on 15/11/29.
 */
public interface IIMService {
    public int TEXT = 0x0;
    public int IMAGE = 0X1;
    public int FILE = 0x2;
    public int VOICE = 0x3;
    public int VIDEO =0x4;
    public void loginAccount();
    public void connect();
    public void sendMessage(int type, String recevicer, String content);
    public void disconnect();
    public void createChat();
    public void registerAccount(String account, String password);

}
