package com.koalitech.bsmart.domain.enity;

import com.koalitech.bsmart.Service.pay.IPayService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zoson on 15/12/13.
 */
public class PayItem {
    private int price = 0;
    private String method = "服务提供安排";
    private String school;
    private User user;

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getService_id() {
        return service_id;
    }

    private int service_id;

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    private String major;
    private String province;
    private String city;
    private String phone;
    private long id_provider = -1;
    private String name_provider;
    private String name_service;
    private String content_service ;
    private String remark;
    private Provider providerType;
    private int payType = ALI;
    public final static int ALI = IPayService.ALI;
    public final static int WEIXIN = IPayService.WEIXIN;

    public PayItem(User user){
        this.user = user;
    }

    public enum Provider{
        PERONAL,COMPANY
    }

    public String getContent_service() {
        return content_service;
    }

    public void setContent_service(String content_service) {
        this.content_service = content_service;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public long getId_buyer() {
        return user.getUid();
    }

    public String getName_buyer() {
        return user.getName();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getId_provider() {
        return id_provider;
    }

    public void setId_provider(int id_provider) {
        this.id_provider = id_provider;
    }

    public String getName_provider() {
        return name_provider;
    }

    public void setName_provider(String name_provider) {
        this.name_provider = name_provider;
    }

    public String getName_service() {
        return name_service;
    }

    public void setName_service(String name_service) {
        this.name_service = name_service;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Provider getProviderType() {
        return providerType;
    }

    public void setProviderType(Provider providerType) {
        this.providerType = providerType;
    }


    public long getUid(){
        return this.user.getUid();
    }
    public static PayItem genPayItemByJson(User user,String json){
        PayItem payItem = new PayItem(user);
        JSONObject jo;
        try{
            jo = new JSONObject(json);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        try {
            payItem.province = jo.getString("province");
        }catch (JSONException e){
            e.printStackTrace();
            payItem.province = "未知省份";
        }

        try {
            payItem.city = jo.getString("city");
        }catch (JSONException e){
            e.printStackTrace();
            payItem.city = "未知城市";
        }
        try {
            JSONArray ja = jo.getJSONArray("school");
            payItem.school = ja.getString(0);
        }catch (JSONException e){
            e.printStackTrace();
            payItem.school = "未知学校";
        }try {
            JSONArray ja = jo.getJSONArray("major");
            payItem.major = ja.getString(0);
        }catch (JSONException e){
            e.printStackTrace();
            payItem.major = "未知专业";
        }try {
            payItem.name_provider = jo.getString("Seller");
        }catch (JSONException e){
            e.printStackTrace();
            payItem.name_provider = "匿名";
        }
        try {
            payItem.name_service = jo.getString("Service_name");
        }catch (JSONException e){
            e.printStackTrace();
            payItem.name_service = "未知服务";
        }

        try {
            payItem.price = Integer.parseInt(jo.getString("price").replace("元",""));
        }catch (JSONException e){
            e.printStackTrace();
            payItem.price = 1;
        }
        try {
            payItem.phone = jo.getString("phone");
        }catch (JSONException e){
            e.printStackTrace();
            payItem.phone = "未知电话";
        }

        return payItem;
    }

}
