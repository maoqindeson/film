package com.leoleo.film.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.leoleo.film.dao.OrderDao;
import com.leoleo.film.entity.Order;
import com.leoleo.film.service.OrderService;
import com.leoleo.film.utils.OrderGoodsName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {


    @Override
    public Order getOrderByOrderid(String orderid) {
        return baseMapper.getOrderByOrderid(orderid);
    }

    @Override
    public List<Order> getOrderByPage(Integer pageNo, Integer pageSize) {
        return baseMapper.getOrderByPage(pageNo, pageSize);
    }

    @Override
    public List<OrderGoodsName> getOrderGoodsNameByOrderid(String orderid) {
        return baseMapper.getOrderGoodsNameByOrderid(orderid);
    }

    @Override
    public int insertOrder(String orderid, String name, String goodsid, BigDecimal price, Integer numbers, Date orderTime, String orderStatus) {
        return baseMapper.insertOrder(orderid, name, goodsid, price, numbers, orderTime, orderStatus);
    }

    @Override
    public int updateOrder(String orderid, String orderStatus) {
        return baseMapper.updateOrder(orderid, orderStatus);
    }
}

