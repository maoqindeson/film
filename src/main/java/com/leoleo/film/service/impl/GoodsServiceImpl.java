package com.leoleo.film.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.leoleo.film.dao.GoodsDao;
import com.leoleo.film.entity.Goods;
import com.leoleo.film.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao,Goods> implements GoodsService {
    @Override
    public Goods getGoodsByGoodsid(String goodsid) {
        return baseMapper.getGoodsByGoodsid(goodsid);
    }

    @Override
    public List<Goods> getGoodsByPage(int pageNo, int pageSize) {
        return baseMapper.getGoodsByPage(pageNo,pageSize);
    }

    @Override
    public int insertGoods(String goodsid, String goodsName, int numbers) {
        return baseMapper.insertGoods(goodsid,goodsName,numbers);
    }

    @Override
    public int updateGoods(String goodsid, int numbers) {
        return baseMapper.updateGoods(goodsid,numbers);
    }
}
