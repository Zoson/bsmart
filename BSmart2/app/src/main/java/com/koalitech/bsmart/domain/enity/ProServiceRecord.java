package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 15/12/20.
 */
public class ProServiceRecord extends ServiceRecord {
    boolean is_finish;
    boolean is_refund;

    public ProServiceRecord(){

    }

    public static ProServiceRecord genProServiceProduct(JSONObject jo){
        ProServiceRecord serviceRecord = new ProServiceRecord();
        try {
            serviceRecord.date = jo.getString("date");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.service_name = jo.getString("Service_name");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.price = (float)jo.getDouble("price");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.seller = jo.getString("seller");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.id = jo.getLong("id");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.is_finish = jo.getBoolean("is_finish");
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            serviceRecord.is_refund = jo.getBoolean("is_refund");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return serviceRecord;
    }
}
