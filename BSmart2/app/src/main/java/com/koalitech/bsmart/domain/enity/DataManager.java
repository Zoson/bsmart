package com.koalitech.bsmart.domain.enity;

import android.util.Log;

import com.koalitech.bsmart.Service.Database.SharedPreference;
import com.koalitech.bsmart.domain.context.BSmartContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Zoson on 15/12/19.
 */
public class DataManager extends BSmartContext {
    private User mMasterUser;
    private HashMap<Long,User> allUsers;
    private List<Inherit> inheritUsers;
    private List<ComService> comServices;
    private List<Thoroughbred> thoroughbreds;
    private List<Thoroughbred> choiceThoroughbreds;
    //静态数据,城市,省份等..
    private List<String> provinces;
    private List<String> majorclassified;
    private HashMap<Integer,List<String>> cities;
    private HashMap<Integer,List<String>> schools;
    private HashMap<Integer,List<String>> majors;

    public List<String> getIndustrys() {
        return industrys;
    }

    public void setIndustrys(String json) {
        if (industrys.size()>0)return;
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                industrys.add(jsonObject.getString("I_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(String json) {
        if (functions.size()>0)return;
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                functions.add(jsonObject.getString("f_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //静态分类数据
    private List<String> industrys;
    private List<String> functions;

    private List<Precommend> precommends;
    private List<Precommend> findPrecommends;
    private List<DynamicInfo> dynamicInfos;
    private List<DynamicInfo> findDynamicInfos;

    private Register register;

    public DataManager(){
        allUsers = new HashMap<Long,User>();
        dynamicInfos = new ArrayList<>();
        findDynamicInfos = new ArrayList<DynamicInfo>();
        findPrecommends = new ArrayList<Precommend>();
        precommends = new ArrayList<>();
        inheritUsers = new ArrayList<>();
        provinces = new ArrayList();
        majorclassified = new ArrayList();
        cities = new HashMap();
        schools = new HashMap();
        majors = new HashMap();
        thoroughbreds = new ArrayList<>();
        choiceThoroughbreds = new ArrayList<>();
        industrys = new ArrayList<>();
        functions = new ArrayList<>();
        comServices = new ArrayList<>();
    }

    public Register createRegister(){
        if (register == null){
            register = new Register();
            return register;
        }else{
            return register;
        }
    }

    public User createMasterUser(long uid,String account,String password){
        this.mMasterUser = new User();
        mMasterUser.setUid(uid);
        mMasterUser.setAccount(account);
        mMasterUser.setPassword(password);
        allUsers.put(uid, mMasterUser);
        getSharedPrefence().set("uid",uid+"");
        return mMasterUser;
    }

    public User getMasterUser(){
        if (mMasterUser==null){
            SharedPreference sharedPreference =getSharedPrefence();
            long uid = Long.parseLong(sharedPreference.get("uid", "-1"));
            String account =sharedPreference.get("account","");
            if (uid==-1)return mMasterUser;
            mMasterUser = new User();
            mMasterUser.setUid(uid);
            mMasterUser.setAccount(account);
        }
        return mMasterUser;
    }

    public List<ComService> getComServices(){
        return comServices;
    }

    public void handleJsonForComServices(String json){
        try {
            JSONArray ja = new JSONArray(json);
            comServices.clear();
            for (int i=0;i<ja.length();++i){
                String js = ja.getString(i);
                ComService comService = ComService.genObjectByJson(js);
                comServices.add(comService);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleJsonForUpdateInheritUsers(String json){
        try {
            JSONArray ja = new JSONArray(json);
            inheritUsers.clear();
            if (inheritUsers.size()==0){
                handleJsonForInheritUsers(json);
                return;
            }

            long uid = inheritUsers.get(0).getUid();
            for (int i=0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                User user  = allUsers.get(jo.getLong("user_ID"));
                user = Inherit.genUserByJson(user, jo);
                allUsers.put(user.getUid(),user);
                Inherit inherit = Inherit.genInheritByJson(user, jo);
                if (inherit.getUid() == mMasterUser.getUid())continue;
                if (uid == user.getUid())break;
                inheritUsers.add(i,inherit);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleJsonForInheritUsers(String json){
        try{
            JSONArray ja = new JSONArray(json);
            int len = inheritUsers.size();
            for(int i=len;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                User user;
                try{
                    user = allUsers.get(jo.getLong("user_ID"));
                }catch (JSONException e){
                    e.printStackTrace();
                    continue;
                }
                user = Inherit.genUserByJson(user, jo);
                allUsers.put(user.getUid(),user);
                if (user.getUid() == mMasterUser.getUid())continue;
                inheritUsers.add(Inherit.genInheritByJson(user, jo));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public List<Inherit> getInheritUsers(){
        return inheritUsers;
    }


    public List<String> getProvinces(){
        return provinces;
    }

    public List<String> getMajorclassified(){
        return majorclassified;
    }

    public void setMajorclassified(List<String> majorclassified){
        this.majorclassified = majorclassified;
    }

    public List<String> getCities(int id){
        return cities.get(id);
    }

    public List<String> getSchools(int id){
        return schools.get(id);
    }

    public void setProvinces(List<String> provinces){
        this.provinces = provinces;
    }

    public List<String> getMajors(int id){
        return majors.get(id);
    }

    public void setCity(int id,List<String> cities){
        this.cities.put(id, cities);
    }

    public void setSchools(int id,List<String> schools){
        this.schools.put(id,schools);
    }

    public void setMajors(int id,List<String> majors){
        this.majors.put(id,majors);
    }

    public List<DynamicInfo> getDynamicInfos(){
        return dynamicInfos;
    }

    public List<DynamicInfo> getFindDynamicInfos() {
        return findDynamicInfos;
    }

    public List<Precommend> getFindPrecommends() {return findPrecommends;}

    public void addDynamicInfo(DynamicInfo dynamicInfo){
        dynamicInfos.add(dynamicInfo);
    }

    public void handleJsonForUpdateDynamicInfos(String json){
        try{
            JSONArray ja = new JSONArray(json);
            if (dynamicInfos.size() == 0){
                handleJsonForDynamicInfos(json);
                return;
            }
            long did = dynamicInfos.get(0).getDid();
            for (int i=0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                User user = allUsers.get(jo.getLong("user_ID"));
                DynamicInfo dynamicInfo;
                user = DynamicInfo.genUserByJson(user, jo);
                dynamicInfo = DynamicInfo.genDynamicInfoByJson(user, jo);
                allUsers.put(user.getUid(),user);
                if (dynamicInfo == null){
                    Log.e("handleJsonForD", "dynamicinfo is null");
                    continue;
                }
                if (dynamicInfo.getDid() == did)break;
                dynamicInfos.add(i,dynamicInfo);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleJsonForOldDynamicInfos(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = allUsers.get(jsonObject.getLong("user_ID"));
                DynamicInfo dynamicInfo;
                user = DynamicInfo.genUserByJson(user, jsonObject);
                dynamicInfo = DynamicInfo.genDynamicInfoByJson(user, jsonObject);
                if(user!=null)
                    allUsers.put(user.getUid(),user);
                if (dynamicInfo == null){
                    Log.e("handleJsonForD", "dynamicinfo is null");
                    continue;
                }
                dynamicInfos.add(dynamicInfo);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleJsonForDynamicInfos(String json){
        try{
            JSONArray jsonArray = new JSONArray(json);
            int len = dynamicInfos.size();
            for (int i=len;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = allUsers.get(jsonObject.getLong("user_ID"));
                DynamicInfo dynamicInfo;
                user = DynamicInfo.genUserByJson(user, jsonObject);
                dynamicInfo = DynamicInfo.genDynamicInfoByJson(user, jsonObject);
                if(user!=null)
                    allUsers.put(user.getUid(),user);
                if (dynamicInfo == null){
                    Log.e("handleJsonForD", "dynamicinfo is null");
                    continue;
                }
                dynamicInfos.add(dynamicInfo);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handlerJsonForFindDynamicInfos(String json) {
        try{
            JSONArray jsonArray = new JSONArray(json);
            findDynamicInfos.clear();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                User user = allUsers.get(jsonObject.getLong("user_ID"));
                DynamicInfo dynamicInfo;
                user = DynamicInfo.genUserByJson(user, jsonObject);
                dynamicInfo = DynamicInfo.genDynamicInfoByJson(user, jsonObject);
                if(user!=null)
                    allUsers.put(user.getUid(),user);
                if (dynamicInfo == null){
                    Log.e("handleJsonForFindD", "dynamicinfo is null");
                    continue;
                }
                Log.i("haclog", dynamicInfo.toString());
                findDynamicInfos.add(dynamicInfo);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void handleJsonForPrecomment(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            int len = precommends.size();
            for (int i=len ;i<jsonArray.length();i++){
                Precommend precommend = Precommend.genPrecommendByJson(jsonArray.getString(i));
                if (precommend==null){
                    Log.e("genPrecommendByJson","precommend is null");
                    continue;
                }
                precommends.add(precommend);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handlerJsonForFindPrecommend(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            findPrecommends.clear();
            for (int i=0 ;i<jsonArray.length();i++){
                Precommend precommend = Precommend.genPrecommendByJson(jsonArray.getString(i));
                if (precommend==null){
                    Log.e("genPrecommendByJson","precommend is null");
                    continue;
                }
                findPrecommends.add(precommend);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void addPrecomment(Precommend precomment){
        precommends.add(precomment);
    }

    public List<Precommend> getPrecommends(){
        return precommends;
    }

    public User getUser(String account){
        for (long uid:allUsers.keySet()){
            User user = allUsers.get(uid);
            if (user.getAccount()!=null){
                if (allUsers.get(uid).getAccount().equals(account)){
                    return allUsers.get(uid);
                }
            }
        }
        return null;
    }

    public List<Thoroughbred> getThoroughbreds(){
        return thoroughbreds;
    }

    public List<Thoroughbred> getChoiceThoroughbreds() {
        return choiceThoroughbreds;
    }

    public void handleJsonForThoroughbreds(String json){
        try {
            JSONArray ja = new JSONArray(json);
            for (int i=thoroughbreds.size();i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                long rid = jo.getLong("user_ID");
                User user = allUsers.get(rid);
                if (user ==null){
                    user = Thoroughbred.genUser(jo);
                }
                Thoroughbred thoroughbred = Thoroughbred.genThoroughbreds(user,ja.getJSONObject(i));
                allUsers.put(rid,user);
                thoroughbreds.add(thoroughbred);

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleJsonForChoiceThoroughbreds(String json){
        try {
            JSONArray ja = new JSONArray(json);
            choiceThoroughbreds.clear();
            for (int i=0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                long rid = jo.getLong("user_ID");
                User user = allUsers.get(rid);
                if (user ==null){
                    user = Thoroughbred.genUser(jo);
                }
                Thoroughbred thoroughbred = Thoroughbred.genThoroughbreds(user,ja.getJSONObject(i));
                allUsers.put(rid,user);
                choiceThoroughbreds.add(thoroughbred);

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public User getUser(long uid){
        User user = allUsers.get(uid);
        return user;
    }

    public void clearUser(){
        this.mMasterUser = null;
    }
    public void addUser(long rid,User user){
        allUsers.put(rid,user);
    }
}
