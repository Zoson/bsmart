package com.koalitech.bsmart.domain.enity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoson on 16/2/1.
 */
public class Experience {
    private List<String> interviewCom;
    private List<String> offerCom;
    private List<Integer> interviewId;
    private List<Integer> offerId;
    private String service1;
    private String service2;
    private String service3;
    private int price;
    private int id;
    private long rid;

    public Experience(){
        interviewCom = new ArrayList<>();
        offerCom = new ArrayList<>();
        interviewId = new ArrayList<>();
        offerId = new ArrayList<>();
    }

    public List<Integer> getInterviewId() {
        return interviewId;
    }

    public List<Integer> getOfferId() {
        return offerId;
    }

    public void handleInterviewByJson(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);

           // int length = jsonArray.length();
            //if(length == 0 && !interviewCom.contains("暂无面试经历")) {
            //    interviewId.add(0);
             //   interviewCom.add("暂无面试经历");
            //} else {
                for(int i=interviewCom.size();i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    interviewId.add(jsonObject.getInt("id"));
                    interviewCom.add(jsonObject.getString("comName"));
                }
           // }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleOfferByJson(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);
          //  int length = jsonArray.length();
           // if(length == 0 && !offerCom.contains("暂未收到offer")) {
           ///     offerId.add(0);
          //      offerCom.add("暂未收到offer");
          //  } else {
                for(int i=offerCom.size();i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    offerId.add(jsonObject.getInt("id"));
                    offerCom.add(jsonObject.getString("comName"));
                }
          //  }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleInterviewIdByJson(String json){
        try{
            JSONObject jo = new JSONObject(json);
            JSONArray js = jo.getJSONArray("ICom_id");
            for(int i=interviewId.size();i<js.length();i++){
                interviewId.add(js.getInt(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleOfferIdByJson(String json){
        try{
            JSONObject jo = new JSONObject(json);
            JSONArray js = jo.getJSONArray("ECom_id");
            for(int i=offerId.size();i<js.length();i++){
                offerId.add(js.getInt(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleServiceByJson(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);
            JSONObject js = jsonArray.getJSONObject(0);
            this.service1 = js.getString("Service1");
            this.service2 = js.getString("Service2");
            this.service3 = js.getString("Service3");
            String price = js.getString("price");
            if (price.equals("")||price.equals("null")){
                this.price = 0;
            }else{
                this.price = Integer.parseInt(price.split("元")[0]);
            }
            this.id = js.getInt("id");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public List<String> getInterviewCom() {
        return interviewCom;
    }

    public void setInterviewCom(List<String> interviewCom) {
        this.interviewCom = interviewCom;
    }

    public List<String> getOfferCom() {
        return offerCom;
    }

    public void setOfferCom(List<String> offerCom) {
        this.offerCom = offerCom;
    }

    public String getService1() {
        return service1;
    }

    public void setService1(String service1) {
        this.service1 = service1;
    }

    public String getService2() {
        return service2;
    }

    public void setService2(String service2) {
        this.service2 = service2;
    }

    public String getService3() {
        return service3;
    }

    public void setService3(String service3) {
        this.service3 = service3;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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



}
