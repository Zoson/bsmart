package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/2/28.
 */
public class ServiceRecord {
    String date;

    public String getDate() {
        return date;
    }

    public String getService_name() {
        return service_name;
    }

    public double getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }

    String service_name;
    double price;
    long id;
    String seller;

    public ServiceRecord(){

    }

    public static ServiceRecord genServiceRecordByJson(JSONObject jo){
        ServiceRecord serviceRecord = new ServiceRecord();
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
            serviceRecord.price = jo.getDouble("price");
        }catch (JSONException e){
            e.printStackTrace();
            serviceRecord.price = 0.0;
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

        return serviceRecord;

    }
}
