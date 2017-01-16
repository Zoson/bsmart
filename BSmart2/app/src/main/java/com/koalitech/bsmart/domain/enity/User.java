package com.koalitech.bsmart.domain.enity;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zoson on 7/19/15.
 */
public class User{
    long uid;
    String account;
    String password;
    String name = "未命名";
    String relationship = "保密";
    Date birthday = new Date(0, 0, 1);
    String gender = "男";
    String introduction = "还未填写自我介绍";
    String image_url;
    WeakReference<Bitmap> image_bm;

    public void initByJson(String json){
        try {
            JSONObject jo = new JSONObject(json);
            uid = jo.getLong("uid");
            account = jo.getString("account");
            password = jo.getString("password");
            name = jo.getString("name");
            relationship = jo.getString("relationship");
            gender = jo.getString("gender");
            String birthday = jo.getString("birthday");
            this.birthday = new Date(birthday);
            introduction = jo.getString("introduction");
            image_url = jo.getString("image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toStringJson(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("uid",uid);
            jo.put("account", account);
            jo.put("password", password);
            jo.put("name", name);
            jo.put("relationship", relationship);
            jo.put("gender", getUid());
            jo.put("birthday", birthday.getTime());
            jo.put("introduction", introduction);
            jo.put("image_url", image_url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public Bitmap getImage_bm_blur() {
        if (image_bm_blur==null)return null;
        return image_bm_blur.get();
    }

    public void setImage_bm_blur(Bitmap image_bm_blur) {
        this.image_bm_blur = new WeakReference(image_bm_blur);
    }

    WeakReference<Bitmap> image_bm_blur;
    Address address = new Address("中国", "广东", "广州", "");

    Contact contact;
    Interest interest;
    PersonalChar personalChar;
    JobsSkill jobsSkills;
    Experience experience;
    List<Jobresume> jobresumes;
    List<Education> educations;
    List<Interest> interests;
    List<Skill> skills;
    List<Speciality> specialities;
    //List<ProfessionalExperience> professionalExperiences;
    List<ServiceRecord> serviceRecords;
    List<SoldServiceRecord> soldServiceRecords;
    List<ComOrderRecord> comOrderRecords;


    Map<String, List<chatMessageEntity>> chatLogs;
    List<chatMessageEntity> chatLogList;

    public List<ProServiceRecord> getProServiceRecords() {
        return proServiceRecords;
    }

    public List<ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    List<ProServiceRecord> proServiceRecords;

    public List<SoldServiceRecord> getSoldServiceRecords(){
        return soldServiceRecords;
    }

    public List<ComOrderRecord> getComOrderRecords(){
        return comOrderRecords;
    }
    public User() {
        jobresumes = new ArrayList();
        educations = new ArrayList();
        interests = new ArrayList();
        skills = new ArrayList();
        specialities = new ArrayList();
        //professionalExperiences = new ArrayList();
        //experience = new Experience();
        //jobsSkills = new JobsSkill();
        serviceRecords = new ArrayList<>();
        proServiceRecords = new ArrayList<>();
        chatLogs = new HashMap<>();
        chatLogList = new ArrayList<>();
        soldServiceRecords = new ArrayList<>();
        comOrderRecords = new ArrayList<>();
    }

    public void setJobsSkills(JobsSkill jobsSkills) {
        this.jobsSkills = jobsSkills;
    }

    public List<chatMessageEntity> getChatLogList(){
        return chatLogList;
    }

    public JobsSkill getJobsSkills() {
        return jobsSkills;
    }

    public List<Jobresume> getJobresumes() {
        return jobresumes;
    }

    public boolean addJobresume(Jobresume jobresume) {
        if (jobresume.getJid() == -1) {
            return false;
        } else {
            jobresume.setUid(this.getUid());
            jobresumes.add(jobresume);
            return true;
        }
    }

    public boolean addEdu(Education education) {
        if (education.getEid() == -1) {
            return false;
        } else {
            education.setUid(this.getUid());
            educations.add(education);
            return true;
        }
    }

    public List<Education> getEducations() {
        return educations;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public PersonalChar getPersonalChar() {
        return personalChar;
    }

    public void setPersonalChar(PersonalChar personalChar) {
        this.personalChar = personalChar;
    }


//    public List<ProfessionalExperience> getProfessionalExperiences() {
//        return professionalExperiences;
//    }

    public void hanldeJsonForServiceRecord(String json) {
        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                ServiceRecord serviceRecord = ServiceRecord.genServiceRecordByJson(jo);
                serviceRecords.add(serviceRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void hanldeJsonForSoldServiceRecord(String json){
        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                SoldServiceRecord serviceRecord = SoldServiceRecord.genSoldServiceRecordByJson(jo);
                soldServiceRecords.add(serviceRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void hanldeJsonForProServiceRecord(String json) {
        try {
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                ProServiceRecord proServiceRecord = ProServiceRecord.genProServiceProduct(jo);
                proServiceRecords.add(proServiceRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handleJsonForJobresumes(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            int len = jobresumes.size();
            for (int i = len; i < jsonArray.length(); i++) {
                addJobresume(Jobresume.genJobresume(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void handleJsonForEdu(String degree, String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            int len = educations.size();
            for (int i = len; i < jsonArray.length(); i++) {
                addEdu(Education.genEducation(degree, jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience){
        this.experience = experience;
    }


    public Address getAddress() {
        return address;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setImage_bm(Bitmap image_bm) {
        this.image_bm = new WeakReference(image_bm);
    }

    public Bitmap getImage_bm() {
        if (image_bm==null){
            return null;
        }
        return image_bm.get();
    }

    public long getUid() {
        return uid;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRelationship() {
        return relationship;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getBirthdayFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(birthday);
        return date;
    }

    public String getGender() {
        return gender;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setAddress(String country, String province, String city) {
        Address address1 = new Address(country, province, city, "");
        this.address = address1;
    }

    public void setAddress(String country, String province, String city, String more) {
        Address address1 = new Address(country, province, city, more);
        this.address = address1;
    }

    public void setAddress(String province, String city) {
        Address address1 = new Address("中国", province, city, "");
        this.address = address1;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void loadLocalData(ArrayList<Map<String, String>> arrayList) {

    }

    public void loadWebData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.uid = Integer.parseInt(jsonObject.getString("id"));
            //this.account = jsonObject.getString("account");
            try {
                this.name = jsonObject.getString("name");
            } catch (JSONException e) {
                name = "未命名";
            }

            try {
                this.relationship = (jsonObject.getString("emotionalState"));
                if (relationship.equals("null")) relationship = "保密";
            } catch (JSONException e) {
                relationship = "保密";
            }

            try {
                this.gender = jsonObject.getString("sex");
            } catch (JSONException e) {
                gender = "男";
            }

            try {
                this.introduction = jsonObject.getString("selfIntro");
                if (introduction.equals("null")) {
                    introduction = "";
                }
            } catch (JSONException e) {
                introduction = "还未填写自我介绍";
            }

            try {
                String date = jsonObject.getString("birthday");
                String[] arr = date.split("-");
                if (arr.length==3){
                    int year = Integer.valueOf(arr[0])-1900;
                    int month = Integer.valueOf(arr[1])-1;
                    int day = Integer.valueOf(arr[2]);
                    this.birthday = new Date(year,month,day);
                }else{
                    this.birthday = new Date(0,0,1);
                }
            } catch (JSONException e) {

            }

            this.image_url = jsonObject.getString("headImg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void insertChatlog(long uid, String name, String date, String content, boolean isComeMe) {
        insertChatlog(account, new chatMessageEntity(name, date, content, isComeMe));
    }

    public void insertChatlog(String account, chatMessageEntity chatlog) {
        insertChatLogToList(chatlog);
        System.out.println("insertChatlog: " + account + " " + chatlog.getText());
        List<chatMessageEntity> chatlogs = this.chatLogs.get(account);
        if (chatlogs == null) {
            chatlogs = new ArrayList<chatMessageEntity>();
            chatLogs.put(account, chatlogs);
        }
        chatlogs.add(chatlog);
    }

    public List<chatMessageEntity> getChatLogs(String account) {
        List<chatMessageEntity> chatlogs = chatLogs.get(account);
        if (chatlogs == null) {
            chatlogs = new ArrayList<chatMessageEntity>();
            chatLogs.put(account, chatlogs);
        }
        return chatLogs.get(account);
    }

    public void insertChatLogToList(chatMessageEntity chatlogs){
        for (int i=0;i<chatLogList.size();i++){
            if (chatlogs.getAccount().equals(chatLogList.get(i).getAccount())){
                chatLogList.remove(i);
                break;
            }
        }
        chatLogList.add(0,chatlogs);
    }

}
