package com.koalitech.bsmart.domain.enity;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/3/19.
 */
public class Thoroughbred {
    private User user;
    private String school;

    public Thoroughbred(User user){
        this.user = user;
    }

    public String getAccount(){
        return this.user.getAccount();
    }

    public String getIntro(){
        return this.user.getIntroduction();
    }

    public String getSchool(){
        return school;
    }

    public String getImageUrl(){
        return user.getImage_url();
    }

    public String getIntroduction(){
        return user.getIntroduction();
    }

    public Bitmap getImage_bm(){
        return user.getImage_bm();
    }

    public String getName(){
        return user.getName();
    }

    public static Thoroughbred genThoroughbreds(User user,JSONObject jsonObject){
        if (user==null){
            user = genUser(jsonObject);
        }
        Thoroughbred thoroughbreds = new Thoroughbred(user);

        try {
            JSONArray ja = jsonObject.getJSONArray("user_school");
            if (ja.length()==0){
                thoroughbreds.school = "未知大学";
            }else{
                thoroughbreds.school = ja.getString(0);
            }
        }catch (JSONException e){
            e.printStackTrace();
            thoroughbreds.school = "未知大学";
        }

        return thoroughbreds;
    }

    public long getUid() {
        return user.getUid();
    }

    public static User genUser(JSONObject jsonObject){
        User user = new User();
        try {
            user.setName(jsonObject.getString("user_name"));
        }catch (JSONException e){
            e.printStackTrace();
            user.setName("BSmart用户");
        }

        try {
            user.setAccount(jsonObject.getString("user_account"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            user.setUid(jsonObject.getLong("user_ID"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            user.setIntroduction(jsonObject.getString("user_selfIntro"));
            if (user.getIntroduction().equals("null")){
                user.setIntroduction("还未自我介绍");
            }
        }catch (JSONException e){
            e.printStackTrace();
            user.setIntroduction("还未自我介绍");
        }

        try {
            user.setImage_url(jsonObject.getString("headImg"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return user;
    }

}
