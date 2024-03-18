package com.aws.aws3.model;

public class history {
    private String id;
    private String data;
    private String category;
    private String location;
    private String car_id;
    private String app_situation;
    private String note;


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getCar_id(){
        return car_id;
    }

    public void setCar_id(String car_id){
        this.car_id = car_id;
    }

    public String getApp_situation(){
        return app_situation;
    }

    public void setApp_situation(String app_situation){
        this.app_situation = app_situation;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }
}
