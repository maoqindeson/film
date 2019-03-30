package com.leoleo.film.controller;

import com.leoleo.film.entity.Price;
import com.leoleo.film.service.PriceService;
import com.leoleo.film.utils.MaoqinObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @PostMapping("getPriceByGoodsid")
    public Price getPriceByGoodsid(String goodsid){
        Price price=  priceService.getPriceByGoodsid(goodsid);
        return price;
    }

    @PostMapping("getPriceByPage")
    public MaoqinObject getPriceByPage(int pageNo,int pageSize){
        int start =(pageNo-1)*pageSize-pageSize;
        List<Price>list = priceService.getPriceByPage(start,pageSize);
        MaoqinObject maoqinObject=new MaoqinObject();
        maoqinObject.setM(1);
        maoqinObject.setMessage("sucess");
        maoqinObject.setObject(list);
        return maoqinObject;
    }

    @PostMapping("insertPrice")
    public int insertPrice(String goodsid, BigDecimal price){
        return priceService.insertPrice(goodsid,price);
    }

    @PostMapping("updatePrice")
    public int updatePrice(String goodsid,BigDecimal price){
        return priceService.updatePrice(goodsid,price);
    }
}
