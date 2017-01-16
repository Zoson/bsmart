package com.koalitech.bsmart.domain.enity;

/**
 * Created by zoson on 7/19/15.
 */
public class Address {
    private String country;
    private String province;
    private String city;
    private String detail = "";

    public Address(String country,String province,String city,String more){
        this.country = country;
        this.province = province;
        this.city = city;
        this.detail = more;
    }

    public String toString(){
        String address = country+" "+province+" "+city+" "+detail;
        return address;
    }
    public String getCountry(){
        return country;
    }
    public String getProvince(){
        return province;
    }
    public String getCity(){
        return city;
    }
    public String getDetail(){
        return detail;
    }
}
