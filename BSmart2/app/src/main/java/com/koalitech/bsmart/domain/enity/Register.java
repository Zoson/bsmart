package com.koalitech.bsmart.domain.enity;


/**
 * Created by Zoson on 16/1/9.
 */
public class Register{
    public final static int PHONE = 0x0;
    public final static int MAIL = 0x1;
    public int type = PHONE;
    public String phone = "";
    public String password = "";
    public String vcode = "";

}
