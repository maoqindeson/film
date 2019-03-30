package com.leoleo.film.utils.DTO;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

@Data
public class ActivityPhoto {
    private int photoId;
    private String photoUrl;
    private int countThumb;
    private int hasThumb;
    @TableField(exist = false)
    private String photoType;
}
