package com.leoleo.film.utils;

import lombok.Data;


@Data
public class MaoqinObject {
    private Integer m;
    private String message;
    private Object object;

    public static MaoqinObject error(String message ){
        MaoqinObject maoqinObject = new MaoqinObject();
        maoqinObject.setM(500);
        maoqinObject.setMessage(message);
        return maoqinObject;
    }

    public static MaoqinObject ok(){
        MaoqinObject maoqinObject =new MaoqinObject();
        maoqinObject.setM(200);
        maoqinObject.setMessage("success");
        return maoqinObject;
    }

    public static MaoqinObject ok(Integer m ,String message) {
        MaoqinObject maoqinObject =new MaoqinObject();
        maoqinObject.setM(m);
        maoqinObject.setMessage(message);
        return maoqinObject;
    }

    public static MaoqinObject ok(Integer m ,String message ,Object object){
        MaoqinObject maoqinObject =new MaoqinObject();
        maoqinObject.setM(m);
        maoqinObject.setMessage(message);
        maoqinObject.setObject(object);
        return maoqinObject;
    }
}
