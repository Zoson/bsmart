package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/6/12.
 */
public class ComOrderRecord {

    int com_server_id;
    String add_Date;
    int price;
    int com_server_Record_id;
    String server_name;
    String StartDate;
    String agreement;
    String EndDate;
    String Img;
    String comName;
    String introduce;
    long id;
    public ComOrderRecord(){

    }

    public static ComOrderRecord genObjectByJson(String json){
        ComOrderRecord comOrderRecord = new ComOrderRecord();

        return comOrderRecord;
    }

    public void initData(String json){
        try {
            JSONObject jo = new JSONObject(json);
            try {
                this.add_Date = jo.getString("add_Date");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.com_server_id = jo.getInt("com_server_id");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.price = jo.getInt("price");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.com_server_Record_id = jo.getInt("com_server_Record_id");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.server_name = jo.getString("server_name");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.StartDate = jo.getString("StartDate");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.agreement = jo.getString("agreement");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.EndDate = jo.getString("EndDate");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.Img = jo.getString("Img");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.comName = jo.getString("comName");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.introduce = jo.getString("introduce");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.id = jo.getLong("id");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.server_name = jo.getString("serverName");
            }catch (JSONException e){
                e.printStackTrace();
            }

        }catch (JSONException e){
           e.printStackTrace();
        }
    }
}
