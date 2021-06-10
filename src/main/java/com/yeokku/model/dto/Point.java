package com.yeokku.model.dto;

public class Point {

    private int pointId;
    private String pointName;
    private int cityId;
    private String cityName;
    private String desc;
    private String img;
    private double lat;
    private double lng;


    public Point() {}

    public Point(String pointName, String cityName) {
        this.pointName = pointName;
        this.cityName = cityName;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
    public String getPointName() {
        return pointName;
    }
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}