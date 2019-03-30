package com.leoleo.film.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.leoleo.film.dao.OrderDao;
import com.leoleo.film.entity.Order;
import com.leoleo.film.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao,Order> implements OrderService {


    @Override
    public Order getOrderByOrderid(String orderid) {
        return baseMapper.getOrderByOrderid(orderid);
    }

    @Override
    public List<Order> getOrderByPage(int pageNo, int pageSize) {
        return baseMapper.getOrderByPage(pageNo,pageSize);
    }

    @Override
    public int insertOrder(String orderid, String name, String goodsid, BigDecimal price, int numbers) {
        return baseMapper.insertOrder(orderid, name, goodsid,  price, numbers);
    }
}

