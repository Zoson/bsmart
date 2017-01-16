package com.koalitech.bsmart.domain.enity;

import java.util.Date;

/**
 * Created by zoson on 7/20/15.
 */
public class Information {
    private Date date;
    private Address address;
    private int gid;
    private int iid;
    private int uid;
    private String publisher;
    public Information(int gid,int iid,int uid,String publisher,Address address){
        this.gid = gid;
        this.iid = iid;
        this.uid = uid;
        this.publisher = publisher;
        this.address = address;
    }
    public int getUid(){
        return uid;
    }

}
