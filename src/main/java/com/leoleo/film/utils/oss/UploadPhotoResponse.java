package com.leoleo.film.utils.oss;

import lombok.Data;

@Data
public class UploadPhotoResponse {
    private String code;
    private String message;
    private long timestamp;
}
