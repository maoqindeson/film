package com.leoleo.film.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("tb_group_buy")
@Data
public class GroupBuy {
    private Integer id;
    private String productId;
    private Integer miniNum;
    private BigDecimal price;
    private Integer status;
    private Date endDate;
    private Date createdDate;
    private Date updatedDate;
}
