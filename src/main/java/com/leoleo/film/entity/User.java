package com.leoleo.film.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("tb_user")
@Data
public class User {
    @TableId
    private int id;
    private String name;
    private String password;
    private String role;
    private BigDecimal balance;

}
