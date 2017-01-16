package com.koalitech.bsmart.domain.enity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoson on 7/20/15.
 */
public class Jobresume {
    private long uid = -1;
    private int jid = -1;
    private String company;
    private String postion;
    private String city;

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    private String province;
    private Duration duration;
    private List<String> introductions;
    private List<Integer> intro_id;

    public Jobresume(){
        introductions = new ArrayList<String>();
        intro_id = new ArrayList<>();
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<String> getIntroduction() {
        return introductions;
    }

    public void setIntroduction(List<String> introduction) {
        this.introductions = introduction;
    }

    public void addIntroduction(String introduction){
        this.introductions.add(introduction);
    }

    public void addIntroduction(int pid,String content){
        this.introductions.add(content);
        this.intro_id.add(pid);
    }

    public static Jobresume genJobresume(String json){
        Jobresume jobresume = new Jobresume();
        try{
            JSONObject js = new JSONObject(json);
            jobresume.province = js.getString("province");
            jobresume.city = js.getString("city");
            jobresume.duration = new Duration(js.getString("sdate"),js.getString("edate"));
            jobresume.jid = js.getInt("wid");
            jobresume.postion = js.getString("position");
            jobresume.company = js.getString("comname");
            JSONArray ja_intro = js.getJSONArray("des");
            JSONArray ja_pid = js.getJSONArray("p_id");
            for (int i=0;i<ja_intro.length();i++){
                int pid = ja_pid.getInt(i);
                jobresume.addIntroduction(pid,ja_intro.getString(i));
            }
        }catch (JSONException e){

        }
        return jobresume;

    }

    public void addPidsByJson(String json){
        try {
            JSONObject js = new JSONObject(json);
            JSONArray ja = js.getJSONArray("pid");
            this.jid = js.getInt("wid");
            intro_id = new ArrayList<>();
            for (int i = 0 ;i<ja.length();i++){
                intro_id.add(ja.getInt(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void removeListItem(int position) {
        introductions.remove(position);
    }

}
