package com.leoleo.film.service;

import com.baomidou.mybatisplus.service.IService;
import com.leoleo.film.entity.Price;

import java.math.BigDecimal;
import java.util.List;

public interface PriceService extends IService<Price> {
    Price getPriceByGoodsid(String goodsid);
    List<Price> getPriceByPage(int pageNo, int pageSize);

    int insertPrice(String goodsid, BigDecimal price);

    int updatePrice(String goodsid, BigDecimal price);
}
