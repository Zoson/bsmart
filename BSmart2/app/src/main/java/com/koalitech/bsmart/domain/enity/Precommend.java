package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by Zoson on 16/1/28.
 */
public class Precommend {
    private long rid = -1;
    private int pid = -1;
    private String pname;
    private String comname;
    private Address waddr;
    private String function;
    private String industry;
    private String salary;
    private String endate;
    private String comdescribe;
    private String pdescribe;

    private String email;   //company email
    public Precommend(){

    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    public long getUid() {
        return rid;
    }

    public void setUid(long rid) {
        this.rid = rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public Address getWaddr() {
        return waddr;
    }

    public void setWaddr(Address waddr) {
        this.waddr = waddr;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEndate() {
        return endate;
    }

    public void setEnddate(String enddate) {
        Calendar calendar = Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String begin = year+"-"+month+"-"+day;
        this.endate = enddate;
    }

    public String getComdescribe() {
        return comdescribe;
    }

    public void setComdescribe(String comdescribe) {
        this.comdescribe = comdescribe;
    }

    public String getPdescribe() {
        return pdescribe;
    }

    public void setPdescribe(String pdescribe) {
        this.pdescribe = pdescribe;
    }

    public static Precommend genPrecommendByJson(String json){
        Precommend precommend = new Precommend();
        try{
            JSONObject jsonObject = new JSONObject(json);
            precommend.function = jsonObject.getString("Function");
            precommend.salary = jsonObject.getString("Salary");
            precommend.endate = jsonObject.getString("Enddate");
            precommend.industry = jsonObject.getString("Industry");
            precommend.comname = jsonObject.getString("Comname");
            precommend.pid = Integer.parseInt(jsonObject.getString("id"));
            precommend.rid = Long.parseLong(jsonObject.getString("RegistrationID"));
            String city = jsonObject.getString("Wcity");
            precommend.waddr = new Address("中国","",city,"");
            precommend.pname = jsonObject.getString("Pname");
            precommend.pdescribe = jsonObject.getString("Pdescribe");
            precommend.comdescribe = jsonObject.getString("Comdescribe");

            //get company email
            precommend.email = jsonObject.getString("com_email");
            if (precommend.email==null||precommend.email.equals("null")){
                precommend.email = "未填写";
            }
        }catch (JSONException e){
            precommend = null;
            e.printStackTrace();
        }
        return precommend;
    }
}
