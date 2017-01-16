package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/4/17.
 */
public class SoldServiceRecord extends ServiceRecord {
    String buyer;

    public String getBuyer(){
        return buyer;
    }

    public static SoldServiceRecord genSoldServiceRecordByJson(JSONObject jo){
        SoldServiceRecord serviceRecord = new SoldServiceRecord();
        try {
            serviceRecord.date = jo.getString("date");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.service_name = jo.getString("Service_name");
            if (serviceRecord.service_name.equals("")||serviceRecord.equals("null")){
                serviceRecord.service_name = "未知服务内容";
            }
        }catch (JSONException e){
            serviceRecord.service_name = "未知服务内容";
            e.printStackTrace();
        }

        try {
            serviceRecord.price = jo.getDouble("price");
        }catch (JSONException e){
            serviceRecord.price = 0.0;
            e.printStackTrace();
        }

        try {
            serviceRecord.buyer = jo.getString("buyer");
            if (serviceRecord.buyer.equals("")||serviceRecord.buyer.equals("null")){
                serviceRecord.buyer = "Bravo用户";
            }
        }catch (JSONException e){
            e.printStackTrace();
            serviceRecord.buyer = "Bravo用户";
        }

        try {
            serviceRecord.id = jo.getLong("id");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return serviceRecord;
    }
}
