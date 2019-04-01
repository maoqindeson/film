package com.leoleo.film.controller;

import com.leoleo.film.entity.Goods;
import com.leoleo.film.service.GoodsService;
import com.leoleo.film.utils.MaoqinObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;



    @PostMapping("getGoodsByGoodsid")
    public Goods getGoodsByGoodsid(String goodsid) {
        Goods goods = goodsService.getGoodsByGoodsid(goodsid);
        return goods;
    }


    @PostMapping("getGoodsByPage")
    public MaoqinObject getGoodsByPage(int pageNo,int pageSize){
        int start =(pageNo-1)*pageSize-pageSize;
        List<Goods>list = goodsService.getGoodsByPage(start,pageSize);
        MaoqinObject maoqinObject=new MaoqinObject();
        maoqinObject.setM(1);
        maoqinObject.setMessage("sucess");
        maoqinObject.setObject(list);
        return maoqinObject;
    }
    @PostMapping("insertGoods")
    public int insertGoods(String goodsid,String goodsName,int numbers){
        return goodsService.insertGoods(goodsid,goodsName,numbers);
    }
    @PostMapping("updateGoods")
    public int updateGoods(String goodsid,int numbers){
        return goodsService.updateGoods(goodsid,numbers);
    }
}
