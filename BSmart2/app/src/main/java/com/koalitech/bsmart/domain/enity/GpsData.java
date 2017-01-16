package com.koalitech.bsmart.domain.enity;

import com.baidu.location.Poi;

import java.util.List;

/**
 * Created by Zoson on 15/9/6.
 */
public class GpsData {
    private String time;
    private int code;
    private double latitude;
    private double longtitude;
    private double radius;
    private double speed;
    private int satellite;
    private double altitude;
    private float direction;
    private String addr;
    private int loctype;
    private String locationDescribe;
    private List<Poi> pois;


}
