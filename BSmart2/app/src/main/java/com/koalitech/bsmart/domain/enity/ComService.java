package com.koalitech.bsmart.domain.enity;

import com.koalitech.bsmart.Service.pay.IPayService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 16/6/12.
 */
public class ComService {
    long rid;

    public long getRid() {
        return rid;
    }

    public int getServerId() {
        return serverId;
    }

    public String getImg() {
        return Img;
    }

    public int getPrice() {
        return price;
    }

    public String getComName() {
        return comName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getAgreement() {
        return agreement;
    }

    public String getComPhone() {
        return comPhone;
    }

    public String getAddress() {
        return address;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    int serverId;
    String Img;
    int price;
    String comName;
    String serverName;

    String StartDate;
    String EndDate;
    String ServerName;
    String introduce;
    String agreement;
    String comPhone;
    String address;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    int payType = IPayService.ALI;
    String method;

    boolean initDetail = false;

    public ComService(){

    }

    public static ComService genObjectByJson(String json){
        ComService comService = new ComService();
        comService.initDataByJson(json);
        return comService;
    }

    public void initDataByJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            try {
                this.rid = jsonObject.getLong("rid");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.serverId = jsonObject.getInt("serverId");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.Img = jsonObject.getString("Img");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.comName = jsonObject.getString("comName");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.serverName = jsonObject.getString("serverName");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.price = jsonObject.getInt("price");
            }catch (JSONException e){
                e.printStackTrace();
                this.price = 0;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isInitDetail(){
        return initDetail;
    }

    public void initDetailDataByJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            initDetail = true;
            try {
                this.StartDate = jsonObject.getString("StartDate");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.EndDate = jsonObject.getString("EndDate");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.ServerName = jsonObject.getString("serverName");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.introduce = jsonObject.getString("introduce");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.agreement = jsonObject.getString("agreement");
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                this.comPhone = jsonObject.getString("comPhone");
                isNull(this.comPhone,"未填写");
            }catch (JSONException e){
                e.printStackTrace();
                this.comPhone = "未填写";
            }
            try {
                this.address = jsonObject.getString("address");
                isNull(this.address,"未知地址");
            }catch (JSONException e){
                e.printStackTrace();
                this.address = "未知地址";
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void isNull(String s,String replace){
        if (s==null)return;
        if (s.equals("null")||s.equals("")){
            s = replace;
        }
    }

}
