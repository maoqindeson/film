package com.leoleo.film.utils;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderGoodsName {
    private String orderid;
    private String name;
    private String goodsid;
    private String goodsName;
    private BigDecimal price;
    private Integer numbers;
    private Date orderTime;
    private String orderStatus;
}
