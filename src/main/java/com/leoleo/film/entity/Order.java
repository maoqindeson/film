package com.leoleo.film.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("tb_order")
@Data
public class Order {
    @TableId
    private Integer id;
    private String orderid;
    private Integer groupBuyId;
    private String name;
    private String goodsid;
    private BigDecimal price;
    private Integer numbers;
    private Date orderTime;
    private String orderStatus;
}
