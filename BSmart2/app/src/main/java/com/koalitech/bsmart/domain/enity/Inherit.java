package com.koalitech.bsmart.domain.enity;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/2/3.
 */
public class Inherit{
    private int around = 0;
    private String school = "未知大学";
    private int serviceId;
    private User user;
    private PayItem payItem;
    public Inherit(User user){
        this.user = user;
    }

    public void setPayItem(PayItem payItem){
        this.payItem = payItem;
    }

    public PayItem getPayItem(){
        return payItem;
    }

    public static Inherit genInheritByJson(User user,JSONObject jo) {
        if (user == null) return null;
        Inherit inherit = new Inherit(user);
        try {
            inherit.school = jo.getJSONArray("user_school").getString(0);
        } catch (JSONException e) {
            inherit.school = "未知大学";
            e.printStackTrace();
        }
        try {
            inherit.around = jo.getInt("user_around");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            inherit.serviceId = jo.getInt("service_id");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return inherit;
    }

    public int getAround(){
        return around;
    }

    public int getServiceId(){
        return serviceId;
    }

    public static User genUserByJson(User user,JSONObject jo){
        if (user == null){
            user = new User();
        }
        try{
            user.account = jo.getString("user_account");
            if (user.account.equals("null")){
                user = null;
                return user;
            }
        }catch (JSONException e){
            e.printStackTrace();
            user = null;
            return user;
        }

        try {
            user.name = jo.getString("user_name");
            if (user.name.equals("null")){
                user.name = "BSmart用户";
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            user.image_url = jo.getString("headImg");
        }catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            user.introduction= jo.getString("user_selfIntro");
            if (user.introduction.equals("null")){
                user.introduction = "还未填写自我介绍";
            }
        }catch (JSONException e) {
            e.printStackTrace();
            user.introduction = "还未填写自我介绍";
        }


        try {
            user.uid = jo.getLong("user_ID");
        }catch (JSONException e) {
            e.printStackTrace();
            user = null;
            return user;
        }

        return user;
    }

    public String getName(){
        return user.getName();
    }

    public String getIntroduction(){
        return user.getIntroduction();
    }

    public String getImage_url(){
        return user.getImage_url();
    }

    public long getUid(){
        return user.getUid();
    }

    public String getSchool() {
        return school;
    }

    public Bitmap getImage_bm(){
        return user.getImage_bm();
    }

    public void setImage_bm(Bitmap bit){
        user.setImage_bm(bit);
    }


    public void setSchool(String school) {
        this.school = school;
    }

    public User getUser() {
        return user;
    }
}
