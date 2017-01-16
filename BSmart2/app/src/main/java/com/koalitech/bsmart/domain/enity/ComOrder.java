package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/6/12.
 */
public class ComOrder {
    /*
    rid，com_server_id（服务ID），s_name（服务名称），s_model（服务方式），payment（支付方式），price（价格
    */

    long rid;
    int com_server_id;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public int getCom_server_id() {
        return com_server_id;
    }

    public void setCom_server_id(int com_server_id) {
        this.com_server_id = com_server_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_model() {
        return s_model;
    }

    public void setS_model(String s_model) {
        this.s_model = s_model;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    String s_name;
    String s_model;
    String payment;
    int price;

    public ComOrder(long rid,int com_server_id){
        this.rid = rid;
        this.com_server_id = com_server_id;
    }

    public ComOrder(){

    }

    public static ComOrder genObjectByJson(String json){
        ComOrder comOrder = new ComOrder();
        try{
            JSONObject jsonObject = new JSONObject(json);
            try {
                comOrder.com_server_id = jsonObject.getInt("com_server_id");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                comOrder.s_name = jsonObject.getString("s_name");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                comOrder.s_model = jsonObject.getString("s_model");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                comOrder.payment = jsonObject.getString("payment");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                comOrder.price = jsonObject.getInt("price");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return comOrder;
    }
}
