package com.leoleo.film.controller;


import com.leoleo.film.entity.Goods;
import com.leoleo.film.entity.Order;
import com.leoleo.film.entity.Price;
import com.leoleo.film.entity.User;
import com.leoleo.film.service.GoodsService;
import com.leoleo.film.service.OrderService;
import com.leoleo.film.service.PriceService;
import com.leoleo.film.service.UserService;
import com.leoleo.film.utils.JWTUtil;
import com.leoleo.film.utils.MaoqinObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.assertj.core.api.BigDecimalAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("buy")
    @RequiresAuthentication
    @Transactional(rollbackFor = Exception.class)
    public synchronized MaoqinObject buy(String orderid, String goodsid, int numbers, HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");    //通过Header拿到用户token
        String name = JWTUtil.getUsername(token);        //解密token拿到用户名
        User user = userService.getUserByName(name);
        Price pr = priceService.getPriceByGoodsid(goodsid);
        BigDecimal price = pr.getPrice();
        if (user.getBalance().compareTo(price) < 0) {
            return MaoqinObject.ok("余额不足");
        }
        Goods go = goodsService.getGoodsByGoodsid(goodsid);
        BigDecimal total =price.multiply(new BigDecimal(numbers));
        BigDecimal balance = user.getBalance().subtract(total);
        int updateResult =userService.updateBalance(name,balance);
        if (0==updateResult ){                                                            //需要判断movieUserService.insertMovieUser结果是否成功，成功了才给用户更新余额userService.updateBalance
            return MaoqinObject.ok("减余额失败，请重试");
        }
        if (updateResult>1){
            throw new Exception();
        }
        if(go.getNumbers()<numbers){
            return MaoqinObject.ok("库存不足");
        }
        int num = go.getNumbers()-numbers;
        goodsService.updateGoods(goodsid,num);
        orderService.insertOrder(orderid,name,goodsid,price,numbers);
          MaoqinObject maoqinObject = new MaoqinObject();
          maoqinObject.setM(1);
          maoqinObject.setMessage("购买成功");
          maoqinObject.setObject(balance);
          return maoqinObject;
    }

    @PostMapping("getOrderByOrderid")
    public Order getOrderByOrderid(String orderid) {
        Order order = orderService.getOrderByOrderid(orderid);
        return order;
    }

    @PostMapping("getOrderByPage")
    public MaoqinObject getOrderByPage(int pageNo, int pageSize) {
        int start = (pageNo - 1) * pageSize - pageSize;
        List<Order> list = orderService.getOrderByPage(start, pageSize);
        MaoqinObject maoqinObject = new MaoqinObject();
        maoqinObject.setM(1);
        maoqinObject.setMessage("sucess");
        maoqinObject.setObject(list);
        return maoqinObject;
    }

    @PostMapping("insertOrder")
    public int insertOrder(String orderid, String name, String goodsid, BigDecimal price, int numbers) {
        return orderService.insertOrder(orderid, name, goodsid, price, numbers);
    }

}
