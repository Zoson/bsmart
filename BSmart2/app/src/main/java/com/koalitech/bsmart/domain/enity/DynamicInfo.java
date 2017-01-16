package com.koalitech.bsmart.domain.enity;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoson on 7/20/15.
 */
public class DynamicInfo {
    private User user;
    private long did = -1;
    private String time;
    private String content;
    private String school;
    private List<Bitmap> images;
    private List<String> imgfiles;

    public List<String> getThumb_imgfiles() {
        return thumb_imgfiles;
    }

    public void setThumb_imgfiles(List<String> thumb_imgfiles) {
        this.thumb_imgfiles = thumb_imgfiles;
    }

    private List<String> thumb_imgfiles;
    private List<Comment> comments;

    public DynamicInfo(User user){
        this.user = user;
        time = "2015-01-01";
        content = "发布者发布的内容";
        images = new ArrayList<Bitmap>();
        imgfiles = new ArrayList<>();
        comments = new ArrayList<Comment>();
        thumb_imgfiles = new ArrayList<>();
    }

    public List<Comment> getComments() {return this.comments; }

    public String getAccount(){
        return user.getAccount();
    }

    public long getUid(){
        return user.getUid();
    }

    public long getDid(){
        return did;
    }

    public void setDid(long did){
        this.did = did;
    }

    public void setIm_head(Bitmap im_head) {
        this.user.setImage_bm(im_head);
    }

    public void setImage(ArrayList<Bitmap> image) {
        this.images = image;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getName() {
        return user.getName();
    }

    public String getPosition() {
        return school;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getIm_head() {
        return user.getImage_bm();
    }

    public String getIm_headUrl() {
        return user.getImage_url();
    }

    public List<Bitmap> getImage() {
        return images;
    }

    public String getIm_url(){
        return user.getImage_url();
    }

    public static DynamicInfo genDynamicInfoByJson(User user,JSONObject jsonObject){
        if (user==null)return null;
        DynamicInfo dynamicInfo = new DynamicInfo(user);
        try{
            dynamicInfo.content = jsonObject.getString("content");
            dynamicInfo.did = jsonObject.getLong("id");
            JSONArray imgsJson = jsonObject.getJSONArray("img");
            JSONArray simgJson = jsonObject.getJSONArray("thumb_img");
            int num = imgsJson.length();
            dynamicInfo.imgfiles = new ArrayList<>();
            for (int i=0;i<num;i++){
                dynamicInfo.imgfiles.add(imgsJson.getString(i));
            }
            for(int i=0;i<simgJson.length();++i)
            {
                dynamicInfo.thumb_imgfiles.add(simgJson.getString(i));
            }
            JSONArray jsonArray = jsonObject.getJSONArray("user_school");
            if (jsonArray.length()!=0){
                dynamicInfo.school = jsonObject.getJSONArray("user_school").getString(0);
            }else{
                dynamicInfo.school = "未知大学";
            }
            //因为后台只返回一个school，所以不能获取jsonarray
//            if("null".equals(jsonObject.getString("user_school"))) {
//                dynamicInfo.school = "未知大学";
//            }else {
//                dynamicInfo.school = jsonObject.getString("user_school");
//            }
            dynamicInfo.time = jsonObject.getString("time");
        }catch (JSONException e){
            e.printStackTrace();
            dynamicInfo = null;
        }
        return dynamicInfo;
    }

    public static User genUserByJson(User user,JSONObject jsonObject){
        if (user == null)
            user = new User();
        try{
            user.name = jsonObject.getString("user_name");
            user.image_url = jsonObject.getString("headImg");
            //JSONArray jsonArray  = jsonObject.getJSONArray("user_school");

            user.uid = jsonObject.getLong("user_ID");
        }catch (JSONException e){
            e.printStackTrace();
            user = null;
        }
        return user;
    }

    public List<String> getImageFiles(){
        return imgfiles;
    }

    public void addImg(int pos,Bitmap bitmap){
        images.add(pos,bitmap);
    }

    public void setImgfiles(List<String> list){
        this.imgfiles = list;
    }

    public class Comment {
        private long id;
        private long rid;
        private Bitmap im_head;
        private String name;
        private String content;
        private String date = "";

        public Comment(long id, long rid, String name, String content, Bitmap im_head, String date) {
            this.name = name;
            this.content = content;
            this.im_head = im_head;
            this.date = date;
        }
        public Comment(String json) {

        }
        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public String getDate() {
            return date;
        }
    }

    public void parseComment(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Comment comment = new Comment(jsonObject.getLong("id"),jsonObject.getLong("com_rid"), jsonObject.getString("com_name"), jsonObject.getString("content"), null, jsonObject.getString("Dy_date"));
                comments.add(comment);
                comment = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
