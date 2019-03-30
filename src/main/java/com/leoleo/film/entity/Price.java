package com.leoleo.film.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("tb_price")
@Data
public class Price {
    @TableId
    private int id;
    private String goodsid;
    private BigDecimal price;

}
