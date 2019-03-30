package com.leoleo.film.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.leoleo.film.entity.Price;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PriceDao extends BaseMapper<Price> {
    Price getPriceByGoodsid(String goodsid);
    List<Price> getPriceByPage(@Param("pageNo")int pageNo, @Param("pageSize")int pageSize);
    int insertPrice(@Param("goodsid")String goodsid, @Param("price")BigDecimal price);
    int updatePrice(@Param("goodsid")String goodsid,@Param("price")BigDecimal price);
}
