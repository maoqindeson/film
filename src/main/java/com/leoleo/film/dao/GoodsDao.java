package com.leoleo.film.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.leoleo.film.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsDao extends BaseMapper<Goods> {
    Goods getGoodsByGoodsid(String goodsid);
    List<Goods> getGoodsByPage(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize);
    int insertGoods(@Param("goodsid") String goodsid,@Param("goodsName")String goodsName,@Param("numbers")int numbers);
    int updateGoods(@Param("goodsid")String goodsid,@Param("numbers")int numbers);
}
