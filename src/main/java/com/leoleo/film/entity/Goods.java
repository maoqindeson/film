package com.leoleo.film.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("tb_goods")
@Data
public class Goods {
    @TableId
    private int id;
    private String goodsid;
    private String goodsName;
    private int numbers;
}
