package com.koalitech.bsmart.domain.enity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zoson on 7/19/15.
 */
public class Duration {
    private Date begin;
    private Date end;
    public Duration(String begin,String end){
        this.begin = strToDate(begin);
        this.end = strToDate(end);
    }
    protected Date strToDate(String d){
        Date date;
        if (d.equals("")){
            date = new Date(0,00,01);
            return date;
        }
        String[] arr_date = d.split("-");
        if (arr_date.length<=1)arr_date = d.split("/");
        String year = arr_date[0];
        String month = arr_date[1];
        String day = arr_date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(year));
        calendar.set(Calendar.MONTH,Integer.parseInt(month));
        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
        date = calendar.getTime();
        return date;
    }
    public String toString_beginDate(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date = format.format(begin);
        return date;
    }
    public String toString_endDate(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date = format.format(end);
        return date;
    }
}
