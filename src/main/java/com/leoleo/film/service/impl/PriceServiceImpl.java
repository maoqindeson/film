package com.leoleo.film.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.leoleo.film.dao.PriceDao;
import com.leoleo.film.entity.Price;
import com.leoleo.film.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service("priceService")
public class PriceServiceImpl extends ServiceImpl<PriceDao,Price>implements PriceService {
    @Override
    public Price getPriceByGoodsid(String goodsid) {
        return baseMapper.getPriceByGoodsid(goodsid);
    }

    @Override
    public List<Price> getPriceByPage(int pageNo, int pageSize) {
        return baseMapper.getPriceByPage(pageNo,pageSize);
    }

    @Override
    public int insertPrice(String goodsid, BigDecimal price) {
        return baseMapper.insertPrice(goodsid,price);
    }

    @Override
    public int updatePrice(String goodsid, BigDecimal price) {
        return baseMapper.updatePrice(goodsid,price);
    }
}
