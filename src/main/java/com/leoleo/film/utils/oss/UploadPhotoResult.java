package com.leoleo.film.utils.oss;

import lombok.Data;

@Data
public class UploadPhotoResult {
    private String code;
    private String msg;
    private UploadPhotoResponse data;
    public static UploadPhotoResult error(String msg){
        UploadPhotoResult u = new UploadPhotoResult();
        u.setCode("500");
        u.setMsg(msg);
        return u;
    }
    public static UploadPhotoResult ok(String msg){
        UploadPhotoResult u = new UploadPhotoResult();
        u.setCode("200");
        u.setMsg(msg);
        return u;
    }
}
