package com.koalitech.bsmart.domain.enity;

import android.graphics.Bitmap;

import com.zoson.cycle.enity.Enity;

/**
 * Created by zoson on 3/22/15.
 */
public class chatMessageEntity extends Enity{
    private long uid;

    private String name;

    private String date;

    private String account;

    private String text;

    private Bitmap im_head;

    private String im_url;

    public long getUid(){
        return uid;
    }

    public void setUid(long uid){
        this.uid = uid;
    }

    private boolean isComMeg = true;

    public String getName() {
        return name;
    }

    public void setImageUrl(String url){
        this.im_url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public String getAccount(){
        return account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
        isComMeg = isComMsg;
    }

    public chatMessageEntity() {
    }

    public String getImageUrl(){
        return im_url;
    }

    public void setImg(Bitmap im_head){
        this.im_head = im_head;
    }
    public Bitmap getImg(){
        return im_head;
    }

    public chatMessageEntity(String account, String date, String text, boolean isComMsg) {
        super();
        this.account = account;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }


}
