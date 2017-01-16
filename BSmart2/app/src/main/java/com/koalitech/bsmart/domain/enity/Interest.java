package com.koalitech.bsmart.domain.enity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zoson on 7/25/15.
 */
public class Interest {
    private long rid;
    private int iid;
    private String movies;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    private String books;
    private String music;
    private String sports;
    private String et;
    public Interest(){

    }

    public static Interest genInterestByJson(String json){
        Interest interest = new Interest();
        try {
            JSONObject jsonObject = new JSONObject(json);
            interest.books = jsonObject.getString("Books");
            interest.sports = jsonObject.getString("Sports");
            interest.movies = jsonObject.getString("Movies");
            interest.music = jsonObject.getString("Music");
            interest.et = jsonObject.getString("ET");
            interest.iid = Integer.parseInt(jsonObject.getString("id"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return interest;
    }

    public String getAll(){
        return books+" "+sports+" "+movies+" "+music+" "+et;
    }
}
