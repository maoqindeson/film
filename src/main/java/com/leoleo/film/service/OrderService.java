package com.leoleo.film.service;

import com.baomidou.mybatisplus.service.IService;
import com.leoleo.film.entity.Order;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService extends IService<Order> {
    Order getOrderByOrderid(String orderid);

    List<Order> getOrderByPage(int pageNo, int pageSize);

    int insertOrder(String orderid, String name, String goodsid, BigDecimal price, int numbers, Date orderTime, String orderStatus);
}
