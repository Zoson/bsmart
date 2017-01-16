package com.koalitech.bsmart.domain.enity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoson on 7/19/15.
 */
public class Education {

    public final static String UNDERGRADUATE = "本科生";
    public final static String POSTGRADUATE = "研究生";
    private long uid;
    private int eid;
    private Address address;
    private String school;
    private String major;
    private Duration duration;
    private boolean ifGraduted;
    private String degree;
    private List<String> stories;
    private List<Integer> sids;
    public Education(){
        stories = new ArrayList<String>();
        sids = new ArrayList<>();
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean isIfGraduted() {
        return ifGraduted;
    }

    public void setIfGraduted(boolean ifGraduted) {
        this.ifGraduted = ifGraduted;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void addStory(int sid,String content){
        sids.add(sid);
        stories.add(content);
    }

    public void addStory(String content){
        stories.add(content);
    }

    public void addSid(int sid){
        sids.add(sid);
    }

    public static Education genEducation(String degree,String json){
        Education education = new Education();
        try{
            JSONObject js = new JSONObject(json);
            education.setDegree(Education.UNDERGRADUATE);
            education.setDegree(degree);
            String province = js.getString("province");
            String city = js.getString("city");
            education.address = new Address("中国",province,city,"");
            education.duration = new Duration(js.getString("startDate"),js.getString("endDate"));
            JSONArray ja_sid = new JSONArray();
            if (degree.equals(Education.POSTGRADUATE)){
                education.eid = js.getInt("p_gid");
                ja_sid = js.getJSONArray("ps_id");
            }else if (degree.equals(Education.UNDERGRADUATE)){
                education.eid = js.getInt("gid");
                ja_sid = js.getJSONArray("gs_id");
            }
            education.school = js.getString("school");
            education.major = js.getString("major");
            JSONArray ja_story = js.getJSONArray("story");

            for (int i=0;i<ja_story.length();i++){
                String story = ja_story.getString(i);
                int sid = ja_sid.getInt(i);
                education.addStory(sid,story);
            }
        }catch (JSONException e){

        }
        return education;
    }

    public List<String> getStories(){
        return stories;
    }

    public List<Integer> getSids(){
        return sids;
    }

    public void addSidsByJson(String json){
        try {
            JSONObject js = new JSONObject(json);
            JSONArray ja = js.getJSONArray("sid");
            if (degree==UNDERGRADUATE){
                this.eid = js.getInt("gid");
            }else{
                this.eid = js.getInt("p_gid");
            }
            sids = new ArrayList<>();
            for (int i = 0 ;i<ja.length();i++){
                sids.add(ja.getInt(i));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void removeListItem(int position) {
        stories.remove(position);
    }

}

