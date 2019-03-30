package com.leoleo.film.utils;

import lombok.Data;

@Data
public class MaoqinObject {
    private int m;
    private String message;
    private Object object;

    public static MaoqinObject ok(String message) {
        MaoqinObject maoqinObject =new MaoqinObject();
        return maoqinObject;
    }

//    public Object getObject() {
//        return object;
//    }
//
//    public void setObject(Object object) {
//        this.object = object;
//    }
//
//    public int getM() {
//        return m;
//    }
//
//    public void setM(int m) {
//        this.m = m;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }


    public static MaoqinObject ok(int m){
    MaoqinObject maoqinObject =new MaoqinObject();
    return maoqinObject;
    }

    public static MaoqinObject ok(Object object){
        MaoqinObject maoqinObject =new MaoqinObject();
        return maoqinObject;
    }
}
