package com.koalitech.bsmart.domain.enity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoson on 16/1/31.
 */
public class JobsSkill {
    private long rid;
    private int en_id = -1;
    private boolean isEnglishChange = false;
    private List<Integer> com_ids;
    private List<Integer> cer_ids;
    private List<Integer> sjob_ids;
    private String englishName;
    private String englishlevel;
    private List<String> computerNames;
    private List<String> computerLevels;
    private List<String> cerNames;
    private List<String> cerContents;
    private List<String> sjobSkills;

    public List<String> getSkillprove() {
        return skillprove;
    }

    private List<String> skillprove;
    public JobsSkill(){
        computerNames = new ArrayList<>();
        computerLevels = new ArrayList<>();
        cerNames = new ArrayList<>();
        cerContents = new ArrayList<>();
        sjobSkills = new ArrayList<>();
        com_ids = new ArrayList<>();
        cer_ids = new ArrayList<>();
        sjob_ids = new ArrayList<>();
        skillprove = new ArrayList<>();
    }

    public long getRid(){
        return rid;
    }

    public void setRid(long rid){
        this.rid = rid;
    }

    public void handleEnglishByJson(String json){
        try {
            JSONObject jo = new JSONObject(json);
            this.en_id = jo.getInt("e_id");
            this.englishlevel = jo.getString("Elevel");
            this.englishName = jo.getString("English");

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleComputerByJson(String json){
        try {
            JSONArray ja = new JSONArray(json);
            for (int i=com_ids.size();i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                this.computerNames.add(jo.getString("computer"));
                this.computerLevels.add(jo.getString("level"));
                this.com_ids.add(jo.getInt("c_id"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void handleCertificateByJson(String json){
        try {
            JSONArray ja = new JSONArray(json);
            for (int i=cer_ids.size();i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                this.cer_ids.add(jo.getInt("c_id"));
                this.cerContents.add(jo.getString("level"));
                this.cerNames.add(jo.getString("certificate"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void handleSJobSkillByJson(String json){
        try{
            JSONArray ja = new JSONArray(json);
            for (int i=sjob_ids.size();i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                this.sjob_ids.add(jo.getInt("s_id"));
                this.skillprove.add(jo.getString("prove"));
                this.sjobSkills.add(jo.getString("sJobSkill"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public boolean isEnglishChange() {
        return isEnglishChange;
    }

    public int getEn_id() {
        return en_id;
    }

    public List<Integer> getCom_ids() {
        return com_ids;
    }

    public List<Integer> getCer_ids() {
        return cer_ids;
    }

    public List<Integer> getSjob_ids() {
        return sjob_ids;
    }

    public void setEnglishlevel(String englishlevel) {
        this.englishlevel = englishlevel;
        isEnglishChange = true;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
        isEnglishChange = true;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getEnglishlevel() {
        if (englishlevel==null||englishlevel.equals("null"))return englishlevel;
        return englishlevel;
    }

    public List<String> getComputerNames() {
        return computerNames;
    }

    public List<String> getComputerLevels() {
        return computerLevels;
    }

    public List<String> getCerNames() {
        return cerNames;
    }

    public List<String> getCerContents() {
        return cerContents;
    }

    public List<String> getSjobSkills() {
        return sjobSkills;
    }

    public void removeComSkill(int pos){
        com_ids.remove(pos);
        computerLevels.remove(pos);
        computerNames.remove(pos);
    }

    public void removeCerSkill(int pos){
        cer_ids.remove(pos);
        cerContents.remove(pos);
        cerNames.remove(pos);
    }

    public void removeSJob(int pos){
        sjob_ids.remove(pos);
        sjobSkills.remove(pos);
        skillprove.remove(pos);
    }

    public String getAllComLevel(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<computerNames.size();i++){
            sb.append(computerNames.get(i)+":"+computerLevels.get(i));
            sb.append(",");
        }
        return sb.toString();
    }

    public String getAllCertificate(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<cerNames.size();i++){
            sb.append(cerNames.get(i)+":"+cerContents.get(i));
            sb.append(",");
        }
        return sb.toString();
    }

    public String getAllSoftSkill(){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<skillprove.size();i++){
            sb.append(sjobSkills.get(i)+":"+skillprove.get(i));
            sb.append(",");
        }
        return sb.toString();
    }

}
