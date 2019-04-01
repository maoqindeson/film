package com.leoleo.film.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.leoleo.film.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface OrderDao extends BaseMapper<Order> {
    Order getOrderByOrderid(String orderid);
    List<Order> getOrderByPage(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize);
    int insertOrder(@Param("orderid")String orderid,
                    @Param("name")String name, @Param("goodsid")String goodsid,
                    @Param("price") BigDecimal price, @Param("numbers")int numbers
            , @Param("orderTime")Date orderTime, @Param("orderStatus")String orderStatus);
}

