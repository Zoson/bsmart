package com.koalitech.bsmart.domain.enity;

/**
 * Created by zoson on 7/28/15.
 */
public class Phone {
    String region;
    int area_code;
    String num;
    public Phone(String region,int area_code,String num){
        this.region = region;
        this.area_code = area_code;
        this.num = num;
    }
    public Phone(String p){
        String[] arr = p.split(" ");
        if (arr.length ==3){
            this.region = arr[0];
            this.area_code = Integer.parseInt(arr[1].substring(1));
            this.num = arr[2];
        }else {
            region = "CN";
            area_code = 86;
            num = p;
        }
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String toString(){
        return region + " "+ "+" + area_code+ " " + num;
    }
    public String getNum(){
        return num;
    }
    public String getRegion(){
        return region;
    }
    public int getArea_code(){
        return area_code;
    }
}
