package com.koalitech.bsmart.domain.enity;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by zoson on 7/20/15.
 */
public class Display {
    private String name;
    private String position;
    private String time;
    private String content;
    private Bitmap im_head;
    private ArrayList<Bitmap> image;
    private int share;
    private int zan;
    private int comment;
    private ArrayList<Comment> comments;
    public Display(String json){

    }
    public Display(){
        name = "koali";
        position = "CEO";
        time = "两小时前";
        content = "点现在加入即代表您同意夸励科技公司的用户协议，隐私以及Cookies政策,点击现在加入即代表您同意夸励科技公司的用户协议，隐私以及Cookies政策";
        share = 1;
        zan = 1;
        comment = 1;
        comments = new ArrayList<Comment>();
        for(int i=0;i<3;i++){
            Comment comment = new Comment(name,position,im_head);
            comments.add(comment);
        }
    }

    public void setIm_head(Bitmap im_head) {
        this.im_head = im_head;
    }

    public void setImage(ArrayList<Bitmap> image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getIm_head() {
        return im_head;
    }

    public ArrayList<Bitmap> getImage() {
        return image;
    }

    public int getShare() {
        return share;
    }

    public int getZan() {
        return zan;
    }

    public int getComment() {
        return comment;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public class Comment{
        private Bitmap im_head;
        private String name;
        private String content;
        private String date = "";
        public Comment(String name,String content,Bitmap im_head){
            this.name = name;
            this.content = content;
            this.im_head = im_head;
        }
        public Comment(String name,String content,Bitmap im_head, String date){
            this.name = name;
            this.content = content;
            this.im_head = im_head;
            this.date = date;
        }
        public Comment(String json){

        }

        public Bitmap getIm_head() {
            return im_head;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }
    }
}
