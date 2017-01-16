package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zoson on 7/20/15.
 */
public class Contact {
    private long rid = -1;
    private int cid = -1;
    private Phone phone;
    private String qq;
    private String wechat;
    private String email;
    private String alipay;
    private String facetime;
    public Contact(){

    }

    public void setUid(long rid){
        this.rid = rid;
    }

    public long getUid(){
        return rid;
    }

    public int getCid(){
        return cid;
    }

    public void setCid(int cid){
        this.cid = cid;
    }

    public void setFacetime(String facetime){
        this.facetime = facetime;
    }

    public String getFacetime(){
        return facetime;
    }

    public void setAlipay(String alipay){
        this.alipay = alipay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = new Phone(phone);
    }

    public void setPhone(String region,int area,String num){
        this.phone = new Phone(region,area,num);
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipay(){
        return alipay;
    }

    public static Contact genContact(String json){
        Contact contact = new Contact();
        try {
            JSONObject jsonObject = new JSONObject(json);
            contact.alipay = jsonObject.getString("Alipay");
            if (contact.alipay==null||contact.alipay.equals("null")){
                contact.alipay = "";
            }
            contact.phone = new Phone(jsonObject.getString("B_Phone"));
            if (contact.phone==null||contact.phone.getNum().equals("null")){
                contact.phone.setNum("");
            }
            contact.email = jsonObject.getString("B_Email");
            if (contact.email==null||contact.email.equals("null")){
                contact.email = "";
            }
            contact.wechat = jsonObject.getString("Wechat");
            if (contact.wechat==null||contact.wechat.equals("null")){
                contact.wechat = "";
            }
            contact.qq = jsonObject.getString("QQ");
            if (contact.qq==null||contact.qq.equals("null")){
                contact.qq = "";
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return contact;
    }
}
