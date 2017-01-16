package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/1/31.
 */
public class PersonalChar {
    private String myLabel1;
    private String myLabel2;
    private String myLabel3;
    private String describe1;
    private String describe2;

    public String getMyLabel1() {
        return myLabel1;
    }

    public void setMyLabel1(String myLabel1) {
        this.myLabel1 = myLabel1;
    }

    public String getMyLabel2() {
        return myLabel2;
    }

    public void setMyLabel2(String myLabel2) {
        this.myLabel2 = myLabel2;
    }

    public String getMyLabel3() {
        return myLabel3;
    }

    public void setMyLabel3(String myLabel3) {
        this.myLabel3 = myLabel3;
    }

    public String getDescribe1() {
        return describe1;
    }

    public void setDescribe1(String describe1) {
        this.describe1 = describe1;
    }

    public String getDescribe2() {
        return describe2;
    }

    public void setDescribe2(String describe2) {
        this.describe2 = describe2;
    }

    public String getDescribe3() {
        return describe3;
    }

    public void setDescribe3(String describe3) {
        this.describe3 = describe3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    private String describe3;
    private int id;
    private long rid;

    public static PersonalChar genPersonalCharByJson(String json){
        PersonalChar personalChar = new PersonalChar();
        try {
            JSONObject js = new JSONObject(json);
            personalChar.describe1 = js.getString("Describe1");
            personalChar.describe2 = js.getString("Describe2");
            personalChar.describe3 = js.getString("Describe3");
            personalChar.myLabel1 = js.getString("MyLabel1");
            personalChar.myLabel2 = js.getString("MyLabel2");
            personalChar.myLabel3 = js.getString("MyLabel3");
            personalChar.id = js.getInt("id");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return personalChar;
    }

    public String getAll(){
        return myLabel1+","+myLabel2+","+myLabel3+","+describe1+","+describe2+","+describe3;
    }
}
