package com.leoleo.film.utils.oss;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SaveActivityResponse {
    private int code;
    private String message;
    private Object data;
    public SaveActivityResponse(){
        this.code = 0;
        this.message = "success";
    }

    public static SaveActivityResponse error(String msg) {
        return error(500, msg);
    }

    public static SaveActivityResponse error(int code, String msg) {
        SaveActivityResponse r = new SaveActivityResponse();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }
    public static SaveActivityResponse ok(String msg) {
        SaveActivityResponse r = new SaveActivityResponse();
        r.setMessage(msg);
        r.setData(new ArrayList<>());
        return r;
    }
}
