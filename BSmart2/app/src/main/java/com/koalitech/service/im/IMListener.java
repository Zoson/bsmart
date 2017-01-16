package com.koalitech.service.im;

/**
 * Created by zoson on 4/19/15.
 */
public interface IMListener {
    public static final int TXT = 0x1;
    public static final int FILE = 0x2;
    public static final int VOICE = 0x3;
    
    public void getMessage(int type, String sender, String content);
}
