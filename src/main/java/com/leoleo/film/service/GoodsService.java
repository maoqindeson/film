package com.leoleo.film.service;

import com.baomidou.mybatisplus.service.IService;
import com.leoleo.film.entity.Goods;

import java.util.List;

public interface GoodsService extends IService<Goods> {
    Goods getGoodsByGoodsid(String goodsid);
    List<Goods> getGoodsByPage(int pageNo, int pageSize);
    int insertGoods( String goodsid,String goodsName,int numbers);
    int updateGoods(String goodsid,int numbers);
}
